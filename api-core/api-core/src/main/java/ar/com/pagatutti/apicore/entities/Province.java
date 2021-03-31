package ar.com.pagatutti.apicore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity(name = "ad_mt_provincia")
public class Province extends BaseMasterTable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provincia")
    @JsonProperty("provincia")
    private String name;

}