package ar.com.pagatutti.apicommons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ActiveAccountCodeRequest {
		@JsonProperty("code")
		private String code;
}
