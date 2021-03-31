package ar.com.pagatutti.smsapibeans.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class CheckCodeSmsResponse {
	private String status;
	private Boolean valid;
}
