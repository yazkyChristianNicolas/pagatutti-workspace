import { Human } from "./human";
import { CheckIdOpportunity } from "./check-id-opportunity";

export class IndividualOpportunity {
    id:number;
    amount: number;
    payments : number;
    phoneVerificationSid:string;
    phoneVerified:boolean;
    phoneVerifiedStatus:string;
    requestStatus:string;
    approved:boolean;
    motivo:string;
    client:Human;
    checkId:CheckIdOpportunity;
     
	constructor(){
    }
}
