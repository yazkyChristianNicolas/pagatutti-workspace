package ar.com.pagatutti.webappback.beans.dto;

import lombok.Data;

@Data
public abstract class MasterTableBaseDTO {
	protected Long id;
	protected boolean activo;
}
