package  ar.com.pagatutti.apicommons.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthorizationTypesEnum {
	BEARER("Bearer "),
	BASIC("Basic ");

    @Getter private String value;
}