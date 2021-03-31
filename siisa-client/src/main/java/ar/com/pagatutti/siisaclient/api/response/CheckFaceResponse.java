package ar.com.pagatutti.siisaclient.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckFaceResponse {
	@JsonProperty("misma_persona")
	private Boolean mismaPersona;
	@JsonProperty("parecido")
	private Integer parecido;
}
