package ar.com.pagatutti.apicorebeans.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import ar.com.pagatutti.apicorebeans.dto.ApiMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedResourceResponse extends BaseApiResponse {

    private Long id;

    @JsonIgnore
    private String uri;

    public CreatedResourceResponse(ApiMessage apiMessage) {
        super(apiMessage);
        try {
            this.id = (Long) apiMessage.getAnything();
        } catch (Exception ignored) {}
    }

    public String getHref() {
        return getUriComponents()
                .map(u -> {
                    String uri = u.toUriString();

                    if ( !uri.contains("localhost") )
                        return uri;

                    try {
                        String hostname = InetAddress.getLocalHost().getHostAddress();
                        String port = u.getPort() < 0 ? "" : ":" + u.getPort();
                        return uri.replaceAll("(localhost[:0-9]*)", hostname + port);
                    } catch (UnknownHostException ignored) { }

                    return uri;
                })
                .orElse(null);
    }

    @JsonIgnore
    public URI getLocation() {
        return getUriComponents()
                .map(UriComponents::toUri)
                .orElse(null);
    }

    private Optional<UriComponents> getUriComponents() {

        if ( null == id || null == uri )
            return Optional.empty();

        return
            Optional.of(
                ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path(uri)
                    .buildAndExpand(id));
    }

}
