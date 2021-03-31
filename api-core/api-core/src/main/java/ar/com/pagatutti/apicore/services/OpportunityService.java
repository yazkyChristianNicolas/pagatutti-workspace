package ar.com.pagatutti.apicore.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.apicore.entities.HumanPerson;
import ar.com.pagatutti.apicore.entities.IndividualOpportunity;
import ar.com.pagatutti.apicore.repositories.BankRepository;
import ar.com.pagatutti.apicore.repositories.IndividualOpportunityRepository;
import ar.com.pagatutti.apicore.repositories.IndividualSegmentRepository;
import ar.com.pagatutti.apicorebeans.api.request.StartOportunityRequest;

@Service
public class OpportunityService {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired 
	private IndividualOpportunityRepository opportunityRepository;
	
	@Autowired 
	private IndividualSegmentRepository individualSegmentRepository;
	
	@Autowired 
	private BankRepository bankRepository;

	
	public IndividualOpportunity saveNewOpportunity(StartOportunityRequest request) throws Exception {
		IndividualOpportunity newOpportunity = modelMapper.map(request, IndividualOpportunity.class);
		HumanPerson opportunityIndividual = modelMapper.map(request, HumanPerson.class);
		
		opportunityIndividual.setFinantialEntity(bankRepository.findById(request.getFinantialEntity()).get());
		opportunityIndividual.setSegment(individualSegmentRepository.findById(request.getIndividualActivity()).get());
		newOpportunity.setClient(opportunityIndividual);
		
		return this.save(newOpportunity);
	}
	
	public IndividualOpportunity finById(Long id) throws Exception{
		return opportunityRepository.findById(id).get();
	}
	
	public IndividualOpportunity save(IndividualOpportunity newOpportunity) {
		return opportunityRepository.save(newOpportunity);
	}

	
}
