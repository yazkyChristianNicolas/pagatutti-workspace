package ar.com.pagatutti.siisaclient.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckIDResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("identifier")
	private String identifier;
	@JsonProperty("nroDoc")
	private String nroDoc;
	@JsonProperty("apellidoNombre")
	private String apellidoNombre;
}
