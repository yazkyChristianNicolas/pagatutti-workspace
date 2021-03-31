package ar.com.pagatutti.apicorebeans.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.pagatutti.apicorebeans.api.request.MTRowRequest;
import ar.com.pagatutti.apicorebeans.api.response.BaseApiResponse;
import ar.com.pagatutti.apicorebeans.api.response.MTDataResponse;
import ar.com.pagatutti.apicorebeans.api.response.MTTypesResponse;

public interface MasterTableControllerInterface {
	
	 @GetMapping
	 public List<String> getAvailableTables();
	 
	 @PostMapping
	 public ResponseEntity<BaseApiResponse>  saveMasterTable(@RequestBody MTRowRequest mtRowRequest);
	 
	 @PutMapping(path = "/{id}")
	 public ResponseEntity<BaseApiResponse> editMasterTable(@RequestBody MTRowRequest mtRowRequest, @PathVariable Long id);
	 
	 @GetMapping(path = "/metadata")
	 public MTTypesResponse getMetadataForTable(@RequestParam(name = "table") String table);
	 
	 @GetMapping(path = "/data")
	 public MTDataResponse getDataForTable(@RequestParam(name = "table") String table);
}
