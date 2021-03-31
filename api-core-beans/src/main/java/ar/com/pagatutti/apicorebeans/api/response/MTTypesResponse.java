package ar.com.pagatutti.apicorebeans.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MTTypesResponse extends BaseApiResponse {

    private String table;
    private Map<String, String> columns;

}