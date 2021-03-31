package ar.com.pagatutti.apicorebeans.api.response;

import ar.com.pagatutti.apicorebeans.dto.ApiMessage;
import ar.com.pagatutti.apicorebeans.enums.AppMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponse {

    private ApiMessage result;

    public BaseApiResponse(AppMessage appMessage) {
        this.result = new ApiMessage(appMessage);
    }

}