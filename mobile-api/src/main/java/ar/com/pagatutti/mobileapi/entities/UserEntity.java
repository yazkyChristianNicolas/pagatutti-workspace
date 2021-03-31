package ar.com.pagatutti.mobileapi.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "creation_date")
    private Date creationDate;
    
    public UserEntity() {
        this.creationDate = new Date();
    }
    
    public UserEntity(String name, String email, String password) {
    	this.name = name; 
    	this.email = email;
    	this.password = password;
        this.creationDate = new Date();
    }
}
