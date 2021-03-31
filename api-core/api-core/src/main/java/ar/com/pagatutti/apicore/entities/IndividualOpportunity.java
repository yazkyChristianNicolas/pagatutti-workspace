package ar.com.pagatutti.apicore.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "individual_opportunity")
public class IndividualOpportunity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
	private Long amount;
	
    @Column(name = "payments")
	private Integer payments;
	
    @Column(name = "terms_and_conditions")
	private Boolean termsAndConditions;

    @Column(name = "phone_verified")
	private Boolean phoneVerified;
	
    @Column(name = "phone_verification_status")
	private String phoneVerifiedStatus;
	
    @Column(name = "phone_verification_sid")
	private String phoneVerificationSid;
    
    @Column(name = "mail_verified")
  	private Boolean mailVerified;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "checkId_id", referencedColumnName = "id")
	private SiisaCheckIdEntity checkId;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
	private HumanPerson client;

    @CreationTimestamp
    @Column(name = "creation_date")
	private Date createdAt;
    
    @Column(name = "approved")
   	private Boolean approved;
	
    @Column(name = "request_status")
    //addEnum
    //@Enumerated(EnumType.STRING)
	private String requestStatus;
    
    @Column(name = "siisa_motivo")
	private String motivo;
    
    @Column(name = "siisa_id")
	private String siisaExcId;
    
}
