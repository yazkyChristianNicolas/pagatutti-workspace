package ar.com.pagatutti.siisaclient.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckIDRequest {
	@JsonProperty("idEntidad")
	private String idEntidad;
	@JsonProperty("idPin")
	private String idPin;
	@JsonProperty("clave")
	private String clave;
	@JsonProperty("imageURL")
	private String imageURL;
	@JsonProperty("imageB64")
	private String imageB64;
}
