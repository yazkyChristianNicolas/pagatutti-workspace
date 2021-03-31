package ar.com.pagatutti.apicorebeans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SiisaCheckIdDTO {
	@JsonProperty("result")
	private String result;
	
	@JsonProperty("identifier")
	private String identifier;
	
	@JsonProperty("nroDoc")
	private String nroDoc;

	@JsonProperty("apellidoNombre")
	private String apellidoNombre;
	
	@JsonProperty("misma_persona")
	private Boolean mismaPersona;
	
	@JsonProperty("parecido")
	private int parecido;
}
