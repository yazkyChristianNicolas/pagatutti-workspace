import { FinantialEntity } from "./finantial-entity";
import { IndividualActivity } from "./individual-activity";

export class Human {
    name: string;
	lastName : string;
	docNumber : string;
	birthday : string;
	areaCode : string;
	cellPhoneNumber : string;
	individualActivity: IndividualActivity;
	finantialEntity: FinantialEntity;
	email : string;
	genre : string;
    termsAndConditions : boolean;

     constructor(){}
}
