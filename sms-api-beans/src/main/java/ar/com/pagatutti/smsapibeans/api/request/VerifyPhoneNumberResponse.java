package ar.com.pagatutti.smsapibeans.api.request;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class VerifyPhoneNumberResponse {
	private String verificationSid;
	private String status;
	private String to;
	//private ZonedDateTime createdAt;
	private Boolean valid;
}
