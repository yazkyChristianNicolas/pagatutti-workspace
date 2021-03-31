package ar.com.pagatutti.apicoreclient.clients;


import org.springframework.cloud.openfeign.FeignClient;

import ar.com.pagatutti.apicorebeans.api.controllers.MasterTableControllerInterface;

@FeignClient(value = "masterTableClient", url = "${api-core.client.masterTable}")
public interface MasterTablesClient extends MasterTableControllerInterface{
	
	
}
