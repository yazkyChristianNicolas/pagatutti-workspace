package ar.com.pagatutti.smsapibeans.api.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsRequest {

		@JsonProperty("phoneNumbers")
		private List<String> numbers;
		
		@JsonProperty("message")
		private String message;

}
