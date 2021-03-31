package ar.com.pagatutti.mobileapi.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.mobileapi.entities.UserEntity;
import ar.com.pagatutti.mobileapi.repositories.UserRepository;


@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    
    public List<UserEntity> list() {
        return userRepository.findAll();
    }
    
    public UserEntity save(UserEntity newUser) {
       return userRepository.saveAndFlush(newUser);
    }
   
    public UserEntity findByEmail(final String email) {
    	List<UserEntity> users = this.userRepository.findByEmail(email);    	
    	return (users == null || users.size() == 0)? null: users.get(0);
    }
    
    public UserEntity findByName(final String name) {
    	UserEntity user = this.userRepository.findOneByName(name);    	
    	return user;
    }
    
    public boolean nameExist(String userName) {
    	UserEntity user = this.findByName(userName);
    	return (null != user);
    }
    
    public boolean emailExist(String email) {
    	List<UserEntity> users = this.userRepository.findByEmail(email);
    	
    	return (null != users && users.size() > 0)?true: false;
    }
    
    public Optional<UserEntity> findById(final Long id) {
        return this.userRepository.findById(id);
    }
  
    
    public UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException {
		 final UserEntity user = this.findByName(userName);
	        
	     if (null != user) {
	        return getUserDetailFromUser(user);
	     }
	     
	    throw new UsernameNotFoundException("User not found with id: " + userName);
	}
    
    public UserDetails getUserDetailFromUser(UserEntity user) {
    	return (UserDetails) new User(user.getName(), user.getPassword(), new ArrayList());
    }
    
}