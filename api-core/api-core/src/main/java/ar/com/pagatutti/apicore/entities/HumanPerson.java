package ar.com.pagatutti.apicore.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "human_person")
public class HumanPerson {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
	private String name;
	
    @Column(name = "lastname")
	private String lastName;
	
    @Column(name = "doc_number")
	private String docNumber;
	
    @Column(name = "birthday")
	private LocalDate birthday;
	
    @Column(name = "core_area")
	private String areaCode;
	
    @Column(name = "phone_number")
	private String cellPhoneNumber;
	
	@ManyToOne()
    @JoinColumn(name = "segment")
	private IndividualSegment segment;
	
    @ManyToOne()
    @JoinColumn(name = "finantialEntity")
	private Bank finantialEntity;
	
    @Column(name = "email")
	private String email;
	
    @Column(name = "genre")
	private String genre;
}
