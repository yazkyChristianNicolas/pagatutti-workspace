package  ar.com.pagatutti.apicommons.beans;

import lombok.Data;

@Data
public class AuthenticatedUserBean {
	private Long id;
	private String name;
	
	public AuthenticatedUserBean(Long id, String name) {
		this.name = name;
		this.id = id;
	}
}

