package  ar.com.pagatutti.apicommons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SendSmsCodeRequest {
	@JsonProperty("tel")
	private String tel;
	
	public SendSmsCodeRequest() {
		
	}
	public SendSmsCodeRequest(String tel) {
		this.tel = tel;
	}
}
