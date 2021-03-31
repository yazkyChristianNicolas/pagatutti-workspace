import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { RequestForLoanRequest } from '../models/request-for-loan-request';
import { VerifyPhoneNumberSmsRequest } from '../models/verify-phone-number-sms-request';
import { CheckSmsCodeRequest } from '../models/check-sms-code-request';


@Injectable({
  providedIn: 'root'
})
export class ApiService {;
   
  httpOptions = { headers: new HttpHeaders({'Content-Type':  'application/json'})};

  constructor(private http: HttpClient,  @Inject('BASE_API_URL') private baseUrl: string) { }

  startLoanRequest(loanRequest:RequestForLoanRequest): Observable<JSON> {
    console.log("Request for Loan");
    console.log(loanRequest);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/new", loanRequest, this.httpOptions);
  }

  sendSmsCode(opportunityId:number, phoneNumber:string): Observable<JSON> {
    console.log("Send sms code: " + opportunityId + " " + phoneNumber);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/"+ opportunityId +"/verify/sms", phoneNumber, this.httpOptions);
  }

  checkSmsCode(opportunityId:number, code:string): Observable<JSON> {
    console.log("Check sms code: " + opportunityId + " " + code);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/"+ opportunityId +"/check/sms", code, this.httpOptions);
  }

  evalOpportunityRequest(opportunityId:number): Observable<JSON> {
    console.log("Eval Opportunity Request: " + opportunityId);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/"+ opportunityId +"/eval", null, this.httpOptions);
  }

  validateMail(token:string): Observable<JSON> {
    return this.http.post<JSON>(this.baseUrl + "/opportunity/check/mail", token, this.httpOptions);
  }

  checkId(opportunityId:number, imageBase64:any): Observable<JSON> {
    console.log("Check Id image: ");
    console.log(imageBase64);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/"+ opportunityId +"/checkId", imageBase64, this.httpOptions);
  }

  checkIdFace(opportunityId:number, imageBase64:any): Observable<JSON> {
    console.log("Check Id Face image: ");
    console.log(imageBase64);
    return this.http.post<JSON>(this.baseUrl + "/opportunity/"+ opportunityId +"/checkIdFace", imageBase64, this.httpOptions);
  }

  handleError(error: HttpErrorResponse){
    return throwError(error);
  }
}
