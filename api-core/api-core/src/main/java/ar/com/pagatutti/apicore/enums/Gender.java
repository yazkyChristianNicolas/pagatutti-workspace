package ar.com.pagatutti.apicore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    M("Masculino"),
    F("Femenino");

    private final String value;

}
