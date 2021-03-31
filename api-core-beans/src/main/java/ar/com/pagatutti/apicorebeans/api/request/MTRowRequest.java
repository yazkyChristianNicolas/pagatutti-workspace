package ar.com.pagatutti.apicorebeans.api.request;

import lombok.Data;

import java.util.Map;

@Data
public class MTRowRequest {

    private String table;
    private Map<String, String> content;

}
