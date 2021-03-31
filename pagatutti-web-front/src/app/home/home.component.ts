import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import {timer} from 'rxjs';
import {take} from 'rxjs/operators';  
import {DomSanitizer} from '@angular/platform-browser';
import {MatIconRegistry} from '@angular/material/icon';
import { FormControl, FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { ConfigurationModel } from '../models/configuration-model';
import { ConfigurationProvider } from '../providers/configuration-provider';
import { ApiService } from '../services/api.service';
import { RequestForLoanRequest } from '../models/request-for-loan-request';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { IndividualOpportunity } from '../models/individual-opportunity';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {

  private configuration: ConfigurationModel;

  max = 60000;
  min = 3000;
  monto = 10000;
  sliderStep = 1000;
  step = 0;
  cuotas = [3, 6, 12, 18, 24];
  unCompleteForm:boolean = false;
  currentOpportunity:IndividualOpportunity = new IndividualOpportunity();
  loading:boolean = false;
  counter$:number;
  smsTimeCountdown:number;
  opportunityResult = undefined;
  loadingMessage = undefined;

  loanRequestForm = new FormGroup({
    payments: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    docNumber: new FormControl('', [Validators.required]),
    birthday: new FormControl('', [Validators.required]),
    areaCode: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
    bank: new FormControl('', [Validators.required]),
    individualActivity: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    genre: new FormControl('', [Validators.required]),
    termsAndConditions: new FormControl('', [Validators.required])
  });

  //Todo meter en una entidad
  cuota:number;
  nombre:String;
  apellido:String;
  dni:String;
  nacimiento:String;
  codigoCelular:String;
  celular:String;
  banco:String;
  email:String;
  sexo:String;
  terminosYcondiciones:boolean;

  //Sms code
  code1:string;
  code2:string;
  code3:string;
  code4:string;

  //Validated Person
  validatedPerson:String;

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer, configurationProvider:ConfigurationProvider, private apiService: ApiService,public dialog: MatDialog) {
    iconRegistry.addSvgIcon('facebook', sanitizer.bypassSecurityTrustResourceUrl('assets/facebook.svg'));
    iconRegistry.addSvgIcon('instagram', sanitizer.bypassSecurityTrustResourceUrl('assets/instagram.svg'));
    iconRegistry.addSvgIcon('twitter', sanitizer.bypassSecurityTrustResourceUrl('assets/twitter.svg'));
    this.configuration = configurationProvider.getConfiguration();
    this.initCounters();
  }


  ngOnInit(): void {
  }

  enviarSolicitud(){
    console.log(this.loanRequestForm.dirty);
    console.log(this.loanRequestForm);

     if(this.loanRequestForm.valid){
            let fingerprint = (<HTMLInputElement>document.getElementById("fingerprint")).value;
            console.log("Fingerprint");
            console.log(fingerprint);
            this.loading = true;
            this.apiService.startLoanRequest(new RequestForLoanRequest(
              this.monto,
              Number(this.loanRequestForm.value.payments),
              this.loanRequestForm.value.name,
              this.loanRequestForm.value.lastName,
              this.loanRequestForm.value.docNumber,
              this.loanRequestForm.value.birthday,
              this.loanRequestForm.value.areaCode,
              this.loanRequestForm.value.phoneNumber,
              this.loanRequestForm.value.individualActivity,
              this.loanRequestForm.value.bank,
              this.loanRequestForm.value.email,
              this.loanRequestForm.value.genre,
              this.loanRequestForm.value.termsAndConditions,
              fingerprint
            ))
            .pipe(catchError(error =>{
                console.log("Start Loan Request Error");
                console.log(error);
                this.loading = false;
                return throwError(error);
            }))
            .subscribe(response => {
                this.currentOpportunity = response["body"];
                console.log(this.currentOpportunity);
                this.loading = false;
                if(this.currentOpportunity.approved){
                    console.log("Opportunity approved, set step 2.");
                    this.sendSmsCode();
                }else{
                  this.setStep(3);
                }
            });
     }else{
        this.unCompleteForm = true;
     }
  }

  setStep(number){
    this.step = number
  }

  sendSmsCode(){
    this.loading = true;
    this.initCounters();
    this.apiService.sendSmsCode(this.currentOpportunity.id, this.currentOpportunity.client.areaCode + this.currentOpportunity.client.cellPhoneNumber)
    .pipe(catchError(error =>{
      console.log("Start Loan Request Error");
      console.log(error);
      this.loading = false;
      return throwError(error);
    }))
    .subscribe(response => {
        this.loading = false;
        this.currentOpportunity = response["body"];
        console.log(this.currentOpportunity);
        timer(1000, 1000).pipe(
          take(this.counter$)).subscribe(x=>{
            this.smsTimeCountdown = --this.smsTimeCountdown;
        })
        this.setStep(1);
    });
  }

  validateSmsCode(){
    this.loading = true;
    if(this.code1 != undefined && this.code2 != undefined && this.code3 != undefined && this.code4 != undefined){
      this.apiService.checkSmsCode(this.currentOpportunity.id, this.code1 + this.code2 + this.code3 + this.code4)
      .pipe(catchError(error =>{
        console.log("Start checkSmsCode Request Error");
        console.log(error);
        this.loading = false;
        return throwError(error);
      }))
      .subscribe(response => {
          this.loading = false;
          this.currentOpportunity = response["body"];
          if(this.currentOpportunity.phoneVerified){
            console.log(this.currentOpportunity);
            this.setStep(3);
          }else{
            alert("Codigo incorrecto");
          }
      });
    }
  }

  clearSolicitud(){
    this.monto = 10000;
    this.loanRequestForm.reset();
    this.code1 = undefined;
    this.code2 = undefined;
    this.code3 = undefined;
    this.code4 = undefined;
    this.validatedPerson = undefined;
    this.step = 0;
    this.currentOpportunity = undefined;
    this.opportunityResult = undefined;
    this.initCounters();
  }

  initCounters(){
    this.counter$ = 60;
    this.smsTimeCountdown = 60;
  }

  isEmpty(str) {
    return (!str || 0 === str.length);
  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  } 

  sortBy(array:Array<any>, prop: string) {
    return array.sort((a, b) => a[prop] > b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
  }

  resetProcess(){
    this.clearSolicitud();
  }


}
