 package ar.com.pagatutti.apicorebeans.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.pagatutti.apicorebeans.enums.AppMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;


@Data
@NoArgsConstructor
public class ApiMessage {

    private String status;
    private String code;
    private String message;

    @JsonIgnore
    private Object anything;

    public ApiMessage(AppMessage appMessage) {
        this.status = appMessage.getStatus();
        this.code = appMessage.getCode();
        this.message = appMessage.getMessage();
    }

    public String formatMessage(String content) {
        this.message = StringUtils.replace(message, "{}", content);
        return message;
    }

    @JsonIgnore
    public boolean isOK() {
        return AppMessage.STATUS_OK.equals(status);
    }

    @JsonIgnore
    public boolean wasResourceCreated() {
        return AppMessage.STATUS_CREATED.equals(status);
    }

    @JsonIgnore
    public boolean isERROR() {
        return AppMessage.STATUS_ERROR.equals(status);
    }

}