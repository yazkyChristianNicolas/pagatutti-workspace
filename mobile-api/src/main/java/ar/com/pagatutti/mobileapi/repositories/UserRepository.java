package ar.com.pagatutti.mobileapi.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import ar.com.pagatutti.mobileapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	List<UserEntity> findByName(final String name);
    
    UserEntity findOneByName(final String name);
  
    List<UserEntity> findByEmail(final String email);
}
