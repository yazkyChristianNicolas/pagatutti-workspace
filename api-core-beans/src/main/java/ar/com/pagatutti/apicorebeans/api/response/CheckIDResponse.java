package ar.com.pagatutti.apicorebeans.api.response;

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
	@JsonProperty("result")
	private String apellidoNombre;
}
