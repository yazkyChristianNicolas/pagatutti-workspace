package ar.com.pagatutti.siisaclient.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthRequest {
	
	@JsonProperty("clientId")
	private String clientId;
	@JsonProperty("pinId")
	private String pinId;
	@JsonProperty("password")
	private String password;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
}
