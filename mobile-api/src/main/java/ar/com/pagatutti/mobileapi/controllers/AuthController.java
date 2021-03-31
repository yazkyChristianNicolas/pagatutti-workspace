package ar.com.pagatutti.mobileapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.com.pagatutti.apicommons.api.AuthenticationRequest;
import ar.com.pagatutti.apicommons.api.AuthenticationResponse;
import ar.com.pagatutti.apicommons.utils.JwtTokenUtils;

import ar.com.pagatutti.mobileapi.api.SignInRequest;
import ar.com.pagatutti.mobileapi.entities.UserEntity;
import ar.com.pagatutti.mobileapi.services.UserService;


@RestController()
public class AuthController {
	
	@Autowired()
	AuthenticationManager authenticationManager;
	
	@Autowired()
	JwtTokenUtils jwtUtils;
	
	@Autowired()
	UserService userService;
	
	@Autowired() 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping(path = "/auth")
	public ResponseEntity<?> authUser(@Valid @RequestBody AuthenticationRequest signInRequest) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		}catch(BadCredentialsException ex) {
			throw new BadCredentialsException("Incorrect userName or password", ex);
		}
		
		final UserEntity user = this.userService.findByName(signInRequest.getEmail());
		final String token = jwtUtils.generateToken(user.getName());
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<?> signInUser(@Valid @RequestBody SignInRequest signInRequest) throws Exception{
		
		if(this.userService.emailExist(signInRequest.getEmail())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exist" );
		}
		
		if(this.userService.nameExist(signInRequest.getUserName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "userName already exist");
		}
		
		UserEntity newUser = this.userService.save(new UserEntity(signInRequest.getUserName(), signInRequest.getEmail(), bCryptPasswordEncoder.encode(signInRequest.getPassword())));
		
		final String token = jwtUtils.generateToken(newUser.getName());
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
}
