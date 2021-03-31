package ar.com.pagatutti.clientapi.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CreateClientRequest {
	
	@JsonProperty("externalId")
	private Long externalId;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("docType")
	private String docType;
	
	@JsonProperty("docNumber")
	private String docNumber;
	
	@JsonProperty("active")
	private Boolean active;
	
	@JsonProperty("termsAndConditions")
	private Boolean termsAndConditions;
}
