package ar.com.pagatutti.apicore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseMasterTable {

    @Column(name = "activo")
    @JsonProperty("activo")
    protected Boolean isActive;

    @CreationTimestamp
    @Column(name = "creado_en")
    @JsonProperty("creado_en")
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    @JsonProperty("actualizado_en")
    protected LocalDateTime updatedAt;

}
