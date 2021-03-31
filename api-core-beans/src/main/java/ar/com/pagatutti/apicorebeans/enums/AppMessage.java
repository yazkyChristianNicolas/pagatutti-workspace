package ar.com.pagatutti.apicorebeans.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppMessage {

    // ----- SUCCESSFUL -----
    OK(AppMessage.STATUS_OK, "OK", "Operación realizada exitosamente."),
    CREATED(AppMessage.STATUS_CREATED, "OK", "Recurso creado exitosamente."),

    // ----- ERRORS -----
    // Master Tables
    E_WRONG_MT_METADATA(AppMessage.STATUS_ERROR, "E100", "Metadata especificada para master table '{}' parece estar errónea."),
    E_POPULATING_MT(AppMessage.STATUS_ERROR, "E101", "Error completando entidad de la master table '{}'."),
    E_MT_NOT_FOUND(AppMessage.STATUS_ERROR, "E102", "No se encontró master table '{}'."),
    E_MT_PK_NOT_FOUND(AppMessage.STATUS_ERROR, "E103", "No se encontró registro solicitado en '{}'."),
    E_MT_MD_NOT_FOUND(AppMessage.STATUS_ERROR, "E104", "No se obtuvo metadata para la tabla '{}'."),
    E_MT_MD_REF_NOT_FOUND(AppMessage.STATUS_ERROR, "E105", "No se obtuvo metadata para la referencia '{}'."),
    E_MT_PROPERTY_ERROR(AppMessage.STATUS_ERROR, "E106", "Error estableciendo propiedad '{}'."),

    // ----- COMMONS -----
    E_INCOMPLETE_CONTENT(AppMessage.STATUS_ERROR, "E107", "El contenido de la solicitud es insuficiente para ser procesado."),
    E_DATA_NOT_FOUND(AppMessage.STATUS_ERROR, "E108", "No se destacaron datos para guardar."),
    E_FIELD_NOT_FOUND(AppMessage.STATUS_ERROR, "E109", "La propiedad '{}' no está definida."),
    E_COPYING_BEANS(AppMessage.STATUS_ERROR, "E110", "Error copiando propiedades de un objeto a otro."),
    E_BEAN_SERIALIZATION(AppMessage.STATUS_ERROR, "E111", "Error serializando bean."),
    E_RESOURCES_CREATION(AppMessage.STATUS_ERROR, "E112", "Los recursos solicitados no fueron creados."),
    E_MT_ID_NOT_SPECIFIED(AppMessage.STATUS_ERROR, "E123", "No se indico id de relación para mastertable asociado a propiedad '{}'."),
    ;

    public static final String STATUS_OK = "200";
    public static final String STATUS_CREATED = "201";
    public static final String STATUS_ERROR = "500";

    private final String status;
    private final String code;
    private final String message;

    public String getMessage() {
        return code + " - " + message;
    }

}
