import { Component, EventEmitter, OnInit, Output,ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { WebcamImage, WebcamInitError, WebcamUtil } from 'ngx-webcam';
import { Observable, Subject, throwError } from 'rxjs';
import { catchError, filter } from 'rxjs/operators';
import {DomSanitizer} from '@angular/platform-browser';
import {MatIconRegistry} from '@angular/material/icon';
import { IndividualOpportunity } from '../models/individual-opportunity';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-mail-validation',
  templateUrl: './mail-validation.component.html',
  styleUrls: ['./mail-validation.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MailValidationComponent implements OnInit {

  // Camara device settings 
  @Output() getPicture = new EventEmitter<WebcamImage>();
  isCameraExist = true;
  multipleCameras = false;
  showWebcam = true;
  errors: WebcamInitError[] = [];
  maxImageSize: 6;
  alreadyValidated = false;

  base64IdImage= undefined;
  base64FaceImage= undefined;

  // webcam snapshot trigger
  private trigger: Subject<void> = new Subject<void>();
  private nextWebcam: Subject<boolean | string> = new Subject<boolean | string>();

  /**********/
  fileToUpload: File = null;
  loading:boolean = false;
  loadingMessage = undefined;
  currentOpportunity:IndividualOpportunity = new IndividualOpportunity();
  checkIdErrorMessage = undefined;
  step:number = 0;

  // Check Email settings
  token: string;
  mailValid = true;

  constructor(iconRegistry: MatIconRegistry, private route: ActivatedRoute, private apiService: ApiService, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon('facebook', sanitizer.bypassSecurityTrustResourceUrl('assets/facebook.svg'));
    iconRegistry.addSvgIcon('instagram', sanitizer.bypassSecurityTrustResourceUrl('assets/instagram.svg'));
    iconRegistry.addSvgIcon('twitter', sanitizer.bypassSecurityTrustResourceUrl('assets/twitter.svg'));
  }

  ngOnInit() {
    this.loading = true;

    WebcamUtil.getAvailableVideoInputs()
    .then((mediaDevices: MediaDeviceInfo[]) => {
      this.isCameraExist = mediaDevices && mediaDevices.length > 0;
      this.multipleCameras = mediaDevices && mediaDevices.length > 1;
      console.log("Camera exist: " +  this.isCameraExist);
      console.log("Multiple Cameras: " + this.multipleCameras);
    });
    
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      console.log("Token:"  + this.token);
      if(this.token != undefined){
          this.apiService.validateMail(this.token)
          .pipe(catchError(error =>{
            console.log("Start Loan Request Error");
            console.log(error);
            this.clearLoading();
            return throwError(error);
        }))
        .subscribe(response => {
          console.log("Token docoded for opportunity");
          console.log(response);
          this.clearLoading();
          this.currentOpportunity = response["body"];
          this.alreadyValidated = (null != this.currentOpportunity.checkId && this.currentOpportunity.checkId.result == "Validado" && this.currentOpportunity.checkId.misma_persona == 'true');
        });
      }
    });
  }

  takeSnapshot(): void {
    this.trigger.next();
  }

  onOffWebCame() {
    this.showWebcam = !this.showWebcam;
  }

  handleInitError(error: WebcamInitError) {
    this.errors.push(error);
  }

  changeWebCame(directionOrDeviceId: boolean | string) {
    this.nextWebcam.next(directionOrDeviceId);
  }

  handleImage(webcamImage: WebcamImage) {
    console.log(webcamImage);
    this.getPicture.emit(webcamImage);
    this.base64FaceImage = this.cleanBase64(webcamImage.imageAsDataUrl);
    this.showWebcam = false;
  }

  get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  get nextWebcamObservable(): Observable<boolean | string> {
    return this.nextWebcam.asObservable();
  }

  handleFileInput(files: FileList) {
    console.log("File in MB "  + files.item(0).size/1024/1024 )
    if(files.item(0).size/1024/1024 > this.maxImageSize){
      this.checkIdErrorMessage = "La imagen no puede superar los 6MB."
    }else{
      this.fileToUpload = files.item(0);
      console.log(this.fileToUpload);
    }
  }

  removeBase64FaceImage(){
    if(!this.loading){
      this.base64FaceImage = null;
      this.showWebcam = true;
    }
  }

  removeFileIdSelected(){
    if(!this.loading){
      this.fileToUpload = null;
    }
  }

  getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  enviarFaceImage(){
    this.loading = true;
    this.loadingMessage = "Subiendo y Analizando Imagen...";
    this.apiService.checkIdFace(this.currentOpportunity.id, this.base64FaceImage).pipe(catchError(error =>{
      console.log("Check Id Face Request Error");
      console.log(error);
      this.checkIdErrorMessage = "Ocurrio un error, no se pudo procesar la imagen."
      this.clearLoading();
      return throwError(error);
    }))
    .subscribe(response => {
      console.log("Check Id Face");
      console.log(response);
      this.clearLoading();                                                                                                                                                                                                                                                                                                           
      this.currentOpportunity = response["body"];
      if(this.currentOpportunity.checkId.misma_persona){
        this.step = 2;
      }else{
        this.checkIdErrorMessage = "Lo sentimos, no pudimos validar tu imagen."
      }

    })
  }

  enviarIdImage(){
    this.loading = true;
    this.loadingMessage = "Subiendo y Analizando Imagen...";
    this.checkIdErrorMessage = undefined;
    this.getBase64(this.fileToUpload).then( data =>
      this.apiService.checkId(this.currentOpportunity.id, this.cleanBase64(data)).pipe(catchError(error =>{
        console.log("CheckId Request Error");
        console.log(error);
        this.checkIdErrorMessage = "Ocurrio un error, no se pudo procesar la imagen."
        this.clearLoading();
        return throwError(error);
      }))
      .subscribe(response => {
        console.log("Check Id");
        console.log(response);
        this.clearLoading();
        this.currentOpportunity = response["body"];
        if(this.currentOpportunity.checkId){
           this.checkIdErrorMessage = (undefined != this.currentOpportunity.checkId.result && undefined == this.currentOpportunity.checkId.identifier)? this.currentOpportunity.checkId.result : undefined;
          if(this.currentOpportunity.checkId.identifier){
            if(this.currentOpportunity.checkId.nroDoc == this.currentOpportunity.client.docNumber){
              this.step = 1;
            }else{
              this.checkIdErrorMessage = "Lo sentimos, no pudimos validar tu DNI."
            }
          }
        }
      })
    );
  }

  clearLoading(){
    this.loading = false;
    this.loadingMessage = undefined;
  }

  cleanBase64(string){
    return string.replace(/^data:image\/[a-z]+;base64,/, "");
  }
}
