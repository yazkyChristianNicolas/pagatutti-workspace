package ar.com.pagatutti.apicoreclient.clients;

import org.springframework.cloud.openfeign.FeignClient;

import ar.com.pagatutti.apicorebeans.api.controllers.OpportunityControllerInterface;

@FeignClient(value = "OpportunityClient", url = "${api-core.client.opportunity}")
public interface OpportunityClient extends OpportunityControllerInterface{

}
