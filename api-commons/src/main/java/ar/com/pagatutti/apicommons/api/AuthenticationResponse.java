package  ar.com.pagatutti.apicommons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthenticationResponse {
		@JsonProperty("token")
		private String token;
		@JsonProperty("expires")
		private Long expires;
		
		public AuthenticationResponse(String token) {
			this.token = token;
		}
		
		public AuthenticationResponse(String token, Long expires) {
			this.token = token;
			this.expires = expires;
		}
}
