package ar.com.pagatutti.apicore.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.apicommons.utils.JwtTokenUtils;
import ar.com.pagatutti.apicore.config.AppConfig;
import ar.com.pagatutti.apicore.entities.IndividualOpportunity;
import ar.com.pagatutti.apicore.entities.SiisaCheckIdEntity;
import ar.com.pagatutti.apicore.services.MailService;
import ar.com.pagatutti.apicore.services.OpportunityService;
import ar.com.pagatutti.apicorebeans.api.controllers.OpportunityControllerInterface;
import ar.com.pagatutti.apicorebeans.api.request.StartOportunityRequest;
import ar.com.pagatutti.apicorebeans.api.request.ValidateTokenRequest;
import ar.com.pagatutti.apicorebeans.dto.CheckFaceRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.CheckIDRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.IndividualOpportunityDTO;
import ar.com.pagatutti.siisaclient.api.request.CheckFaceRequest;
import ar.com.pagatutti.siisaclient.api.request.CheckIDRequest;
import ar.com.pagatutti.siisaclient.api.request.ExecutePolicyWithDetailsRequest;
import ar.com.pagatutti.siisaclient.api.request.ExecutePolicyWithDetailsRequestParams;
import ar.com.pagatutti.siisaclient.api.response.CheckFaceResponse;
import ar.com.pagatutti.siisaclient.api.response.CheckIDResponse;
import ar.com.pagatutti.siisaclient.api.response.ExecutePolicyWithDetailsResponse;
import ar.com.pagatutti.siisaclient.service.SiisaService;
import ar.com.pagatutti.smsapibeans.api.request.CheckCodeSmsRequest;
import ar.com.pagatutti.smsapibeans.api.request.VerifyPhoneNumberResponse;
import ar.com.pagatutti.smsapibeans.api.response.CheckCodeSmsResponse;
import ar.com.pagatutti.smsapiclient.clients.SmsApiClient;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/opportunity")
public class OpportunityController implements OpportunityControllerInterface  {
	
	Logger logger = LoggerFactory.getLogger(OpportunityController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OpportunityService opportunityService;
	
	@Autowired
	private SiisaService siisaService;
	
	@Autowired
	private MailService mailService;
	
	private final SmsApiClient smsClient;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private JwtTokenUtils jwtUtils;
	
	@Override
	public ResponseEntity<IndividualOpportunityDTO>  saveNewOpportunity(StartOportunityRequest request) throws Exception {
		return ResponseEntity.ok(modelMapper.map(opportunityService.saveNewOpportunity(request), IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> sendSms(Long id, String phoneNumber) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);
		
		if(null == currentOpportunity) {
			//throws not found exception
		}
		
		ResponseEntity<VerifyPhoneNumberResponse> response = smsClient.sendSms(phoneNumber);
		currentOpportunity.setPhoneVerificationSid(response.getBody().getVerificationSid());
		currentOpportunity.setPhoneVerifiedStatus(response.getBody().getStatus());
		currentOpportunity.setPhoneVerified(response.getBody().getValid());
		
		currentOpportunity = opportunityService.save(currentOpportunity);
		
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> checkSms(Long id, String code) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);

		if(null == currentOpportunity) {
			//throws not found exception
		}
		
		ResponseEntity<CheckCodeSmsResponse>  checkCodeSmsResponse = smsClient.checkSms(new CheckCodeSmsRequest(currentOpportunity.getClient().getAreaCode().concat(currentOpportunity.getClient().getCellPhoneNumber()), code));
		
		currentOpportunity.setPhoneVerified(checkCodeSmsResponse.getBody().getValid());
		currentOpportunity.setPhoneVerifiedStatus(checkCodeSmsResponse.getBody().getStatus()); 
		 
		currentOpportunity = opportunityService.save(currentOpportunity);
		
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> evalIndividualRequest(Long id, @RequestBody String siisaFingerprint) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);

		if(null == currentOpportunity) {
			//throws not found exception
		}
		
		ExecutePolicyWithDetailsRequestParams request = new ExecutePolicyWithDetailsRequestParams();
		request.setApellidoNombre(currentOpportunity.getClient().getLastName() + " " + currentOpportunity.getClient().getName());
		request.setNroDoc(currentOpportunity.getClient().getDocNumber());
		request.setSexo(currentOpportunity.getClient().getGenre());
		request.setFingerprint((null != siisaFingerprint)? siisaFingerprint : " ");
		request.setSitLaboral(currentOpportunity.getClient().getSegment().getActividad());
		
		ExecutePolicyWithDetailsResponse evalResponse = siisaService.executePolicyWithDetailsRequest(1l, new ExecutePolicyWithDetailsRequest(request));
		
		currentOpportunity.setRequestStatus(evalResponse.getVariables().getDecisionResult());
		currentOpportunity.setApproved(evalResponse.getVariables().isApproved());
		currentOpportunity.setMotivo(evalResponse.getVariables().getMotivo());
		currentOpportunity.setSiisaExcId(evalResponse.getVariables().getCurrentExecId());
		
		
		currentOpportunity = opportunityService.save(currentOpportunity);
		
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> sendNotificationResult(Long id) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);

		if(null == currentOpportunity) {
			//throws not found exception
		}
		
		if(null != currentOpportunity.getApproved() && null != currentOpportunity.getPhoneVerified() && currentOpportunity.getApproved() && currentOpportunity.getPhoneVerified()) {
			mailService.sendEmail(currentOpportunity, jwtUtils.generateToken(String.valueOf(currentOpportunity.getId())));
		}
		
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> checkEmail(ValidateTokenRequest request) throws Exception {
		Long id = Long.valueOf(jwtUtils.getUserNameFromToken(request.getToken()));
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);
		currentOpportunity.setMailVerified(true);
		currentOpportunity = opportunityService.save(currentOpportunity);
		
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> validateId(Long id, CheckIDRequestDTO request) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);
		CheckIDRequest checkIDRequest = modelMapper.map(request, CheckIDRequest.class);
		checkIDRequest.setIdEntidad(appConfig.getSiisaIdEntidad());
		checkIDRequest.setClave(appConfig.getSiisaIdClave());
		checkIDRequest.setIdPin(appConfig.getSiisaIdPin());
		CheckIDResponse response = this.siisaService.checkId(checkIDRequest);
		SiisaCheckIdEntity checkIdResult = (null != currentOpportunity.getCheckId())? currentOpportunity.getCheckId(): new SiisaCheckIdEntity();
		checkIdResult.setResult(response.getResult());
		checkIdResult.setApellidoNombre(response.getApellidoNombre());
		checkIdResult.setNroDoc(response.getNroDoc());
		checkIdResult.setIdentifier(response.getIdentifier());
		currentOpportunity.setCheckId(checkIdResult);
		//Validar si el numero de DNI no es el mismo que el de la opportunidad, rechazarla
		
		currentOpportunity = opportunityService.save(currentOpportunity);
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> validateIdFace(Long id, CheckFaceRequestDTO requestDto) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);
		CheckFaceRequest request = modelMapper.map(requestDto, CheckFaceRequest.class);
		if(null != currentOpportunity.getCheckId() && null != currentOpportunity.getCheckId().getIdentifier()) {
			request.setIdentifier(currentOpportunity.getCheckId().getIdentifier());
		}
		CheckFaceResponse response = this.siisaService.checkFace(request);
		logger.info("CheckFaceResponse");
		logger.info(response.toString());
		
		currentOpportunity.getCheckId().setMismaPersona(response.getMismaPersona());
		currentOpportunity.getCheckId().setParecido(response.getParecido());
		currentOpportunity = opportunityService.save(currentOpportunity);

		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	@Override
	public ResponseEntity<IndividualOpportunityDTO> sendLoanDetailEmail(Long id) throws Exception {
		IndividualOpportunity currentOpportunity = opportunityService.finById(id);
		mailService.sendLoanRequestDetailEmail(currentOpportunity);
		return ResponseEntity.ok(modelMapper.map(currentOpportunity, IndividualOpportunityDTO.class));
	}

	
}
