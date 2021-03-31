package ar.com.pagatutti.apicorebeans.api.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class StartOportunityRequest {
	 private Long amount;
	 private Integer payments;
	 private String name;
	 private String lastName;
	 private String docNumber;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale="es_AR")
	 private LocalDate birthday;
	 private String areaCode;
	 private String cellPhoneNumber;
	 private Long individualActivity;
	 private Long finantialEntity;
	 private String email;
	 private String genre;
	 private Boolean termsAndConditions;
	 private String fingerprint;
}
