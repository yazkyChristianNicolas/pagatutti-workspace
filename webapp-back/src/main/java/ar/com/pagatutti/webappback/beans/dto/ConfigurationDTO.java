package ar.com.pagatutti.webappback.beans.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConfigurationDTO {
		private List<FinantialEntityDTO> financialEntities;
		private List<IndividualActivityDTO> individualActivities;
}
