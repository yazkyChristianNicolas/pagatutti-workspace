package ar.com.pagatutti.apicore.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.pagatutti.apicore.entities.IndividualOpportunity;


@Repository
public interface IndividualOpportunityRepository extends JpaRepository<IndividualOpportunity, Long> {
	
}