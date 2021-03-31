package ar.com.pagatutti.smsapiclient.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ar.com.pagatutti.smsapibeans.api.controllers.TwilioSmsControllerInterface;


@FeignClient(value = "SmsApiClient", url = "${api-sms.client.twilio}")
public interface SmsApiClient extends TwilioSmsControllerInterface{
	
	
}
