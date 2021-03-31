package ar.com.pagatutti.siisaclient.api;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PolicyWithDetailsParamsEnum {
    ApellidoNombre("Apellido_Nombre"), 
    FingerPrint("fingerprint"),
    NroDoc("nrodoc"),
    Sexo("sexo"),
    SitLaboral("Sit_Laboral");


    @Getter private String value;
}