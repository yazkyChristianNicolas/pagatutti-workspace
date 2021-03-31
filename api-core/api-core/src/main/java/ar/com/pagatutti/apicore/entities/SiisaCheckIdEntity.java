package ar.com.pagatutti.apicore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity(name = "check_id")
public class SiisaCheckIdEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "result")
	private String result;
 
    @Column(name = "identifier")
	private String identifier;
    
    @Column(name = "nroDoc")
	private String nroDoc;
    
    @Column(name = "apellidoNombre")
	private String apellidoNombre;
    
    @Column(name ="misma_persona")
	private Boolean mismaPersona;
    
    @Column(name ="parecido")
	private Integer parecido;
	
}
