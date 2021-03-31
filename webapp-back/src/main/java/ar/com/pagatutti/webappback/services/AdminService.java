package ar.com.pagatutti.webappback.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.apicorebeans.enums.MasterTablesEnum;
import ar.com.pagatutti.apicoreclient.clients.MasterTablesClient;
import ar.com.pagatutti.webappback.beans.dto.ConfigurationDTO;
import ar.com.pagatutti.webappback.beans.dto.FinantialEntityDTO;
import ar.com.pagatutti.webappback.beans.dto.IndividualActivityDTO;
import ar.com.pagatutti.webappback.beans.dto.MasterTableBaseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final MasterTablesClient masterTableClient;
	
	@Autowired
	private ModelMapper modelMapper;

	public ConfigurationDTO getBaseConfiguration() throws Exception {
		ConfigurationDTO configuration = new ConfigurationDTO();
		configuration.setFinancialEntities(getMTDataToList(MasterTablesEnum.FinancialEntites, new ParameterizedTypeReference<List<FinantialEntityDTO>>() {}, FinantialEntityDTO.class ));
		configuration.setIndividualActivities(getMTDataToList(MasterTablesEnum.IndividualSegment, new ParameterizedTypeReference<List<IndividualActivityDTO>>() {}, IndividualActivityDTO.class ));
		
		return configuration;
	}
	
	public <T> List<T> getMTDataToList(MasterTablesEnum masterTable, ParameterizedTypeReference<List<T>> resultClass, Class<T> classMap){
		List<Object> objectList = masterTableClient.getDataForTable(masterTable.getValue()).getData();
		 return objectList.stream()
				.map((object)-> modelMapper.map(object, classMap))
				.filter((object)-> ((MasterTableBaseDTO) object).isActivo())
				.collect(Collectors.toList());
	}


}
