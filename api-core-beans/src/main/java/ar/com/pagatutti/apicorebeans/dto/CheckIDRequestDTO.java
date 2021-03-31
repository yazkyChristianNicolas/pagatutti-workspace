package ar.com.pagatutti.apicorebeans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@Data
public class CheckIDRequestDTO {
	@JsonProperty("imageURL")
	private String imageURL;
	@JsonProperty("imageB64")
	private String imageB64;
}
