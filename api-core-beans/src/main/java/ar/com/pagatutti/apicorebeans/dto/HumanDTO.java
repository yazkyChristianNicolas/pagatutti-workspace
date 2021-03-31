package ar.com.pagatutti.apicorebeans.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class HumanDTO {
	
		private String name;
		
		private String lastName;
		
		private String docNumber;
		
		private LocalDate birthday;
		
		private String areaCode;
		
		private String cellPhoneNumber;
		
		private String email;
		
		private String genre;
}
