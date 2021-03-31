package ar.com.pagatutti.webappback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.webappback.beans.dto.ConfigurationDTO;
import ar.com.pagatutti.webappback.services.AdminService;

@RestController
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	

	@GetMapping(value="/configuration")
	public ResponseEntity<ConfigurationDTO> getConfiguration() throws Exception {
		return ResponseEntity.ok(adminService.getBaseConfiguration());
	}

}
