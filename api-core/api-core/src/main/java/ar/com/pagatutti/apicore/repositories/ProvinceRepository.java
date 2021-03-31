package ar.com.pagatutti.apicore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.pagatutti.apicore.entities.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
	
}