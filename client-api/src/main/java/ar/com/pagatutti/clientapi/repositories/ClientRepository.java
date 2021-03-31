package ar.com.pagatutti.clientapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.pagatutti.clientapi.entities.ClientEntity;

@Repository
public interface ClientRepository  extends CrudRepository<ClientEntity, Long> {
	ClientEntity findByExternalId(Long externarId);
}