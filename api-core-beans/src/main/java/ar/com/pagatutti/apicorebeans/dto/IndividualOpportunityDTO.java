package ar.com.pagatutti.apicorebeans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class IndividualOpportunityDTO {
	
    private Long id;

	private Long amount;
	
	private Integer payments;
	
	private Boolean termsAndConditions;

	private Boolean phoneVerified;
	
	private String phoneVerifiedStatus;
	
	private String phoneVerificationSid;
	
  	private Boolean mailVerified;
	
	private HumanDTO client;
	
	private String requestStatus;
	
	private Boolean approved;
		
	private String motivo;
    
	private String fingerprint;
	
	private SiisaCheckIdDTO checkId;

}
