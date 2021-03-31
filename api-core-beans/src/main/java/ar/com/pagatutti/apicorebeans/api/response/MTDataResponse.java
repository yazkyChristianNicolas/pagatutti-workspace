package ar.com.pagatutti.apicorebeans.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MTDataResponse extends MTTypesResponse {

    private List<Object> data;

}