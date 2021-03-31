package ar.com.pagatutti.apicore.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity(name = "ad_mt_banco")
public class Bank extends BaseMasterTable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banco")
    @JsonProperty("banco")
    private String name;

}