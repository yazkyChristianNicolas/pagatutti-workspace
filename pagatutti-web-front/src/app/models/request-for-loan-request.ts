
export class RequestForLoanRequest {
     amount: number;
	 payments : number;
	 name: string;
	 lastName : string;
	 docNumber : string;
	 birthday : string;
	 areaCode : string;
	 cellPhoneNumber : string;
	 individualActivity: number;
	 finantialEntity: number;
	 email : string;
	 genre : string;
     termsAndConditions : boolean;
	 fingerprint:string;
     
	 constructor(amount: number,
		payments : number,
		name: string,
		lastName : string,
		docNumber : string,
		birthday : string,
		areaCode : string,
		cellPhoneNumber : string,
		individualActivity: number,
		finantialEntity: number,
		email : string,
		genre : string,
		termsAndConditions :boolean,
		fingerprint:string
		){
		this.amount = amount;
		this.payments = payments;
		this.name = name;
		this.lastName = lastName;
		this.docNumber = docNumber;
		this.birthday = birthday;
		this.areaCode = areaCode;
		this.cellPhoneNumber = cellPhoneNumber;
		this.individualActivity = individualActivity;
		this.finantialEntity = finantialEntity;
		this.email = email;
		this.genre = genre;
		this.termsAndConditions = termsAndConditions;
		this.fingerprint = fingerprint;
     }
}
