package  ar.com.pagatutti.apicommons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthenticationRequest {
	@JsonProperty("email")
	String email;
	@JsonProperty("password")
	String password;
}
