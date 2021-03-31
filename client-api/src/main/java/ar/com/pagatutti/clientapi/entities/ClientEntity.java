package ar.com.pagatutti.clientapi.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CLIENTS")
@Data
public class ClientEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
	@Column(name ="externalId")
	private Long externalId;
	
	@Column(name ="firstName")
	private String firstName;
	
	@Column(name ="lastName")
	private String lastName;
	
	@Column(name ="fullName")
	private String fullName;
	
	@Column(name ="docType")
	private String docType;
	
	@Column(name = "docNumber", unique = true)
	private String docNumber;
	
	@Column(name ="active")
	private Boolean active;
	
	@Column(name = "active_date")
	private Date activeDate;
	
	@Column(name ="termsAndConditions")
	private Boolean termsAndConditions;
    
    @Column(name = "creation_date")
    private Date creationDate;
    
    public ClientEntity() {
        this.creationDate = new Date();
    }
   
}
