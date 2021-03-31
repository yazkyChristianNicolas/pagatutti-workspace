package ar.com.pagatutti.clientapi.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SignInRequest {
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
}
