package ar.com.pagatutti.clientapi.api;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SmsRequest {

		@JsonProperty("phoneNumbers")
		private List<String> numbers = new ArrayList<String>();
		
		@JsonProperty("message")
		private String message;
	
		public SmsRequest(String phoneNumber, String message) {
			this.numbers.add(phoneNumber);
			this.message = message;
		}
		
}
