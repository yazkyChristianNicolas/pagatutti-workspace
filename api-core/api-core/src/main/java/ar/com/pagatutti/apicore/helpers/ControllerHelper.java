package ar.com.pagatutti.apicore.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.pagatutti.apicorebeans.api.response.BaseApiResponse;
import ar.com.pagatutti.apicorebeans.api.response.CreatedResourceResponse;
import ar.com.pagatutti.apicorebeans.dto.ApiMessage;
import ar.com.pagatutti.apicorebeans.enums.AppMessage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ControllerHelper {

    public static ResponseEntity<CreatedResourceResponse>
        newResourceResponse(ApiMessage apiMessage, String resourceURI) {

        CreatedResourceResponse response = new CreatedResourceResponse(apiMessage);
        response.setUri(resourceURI);

        return ResponseEntity
                .status(apiMessage.isOK() ? HttpStatus.OK:
                            apiMessage.wasResourceCreated() ?
                                HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .location(response.getLocation())
                .body(response);
    }

    public static ResponseEntity<BaseApiResponse>
        generateResponse(ApiMessage apiMessage) {

        HttpStatus httpStatus = apiMessage.isOK() ?
            HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new BaseApiResponse(apiMessage), httpStatus);
    }

    public static <T> ResponseEntity<T> responseWithStatus(T object) {
        return responseWithStatus(object, null);
    }

    public static <T> ResponseEntity<T> responseWithStatus(T object, ApiMessage apiMessage) {
        // Status code used by this method:
        // 404 - Object null or not present (when it's Optional)
        // 400 - ApiMessage is ERROR
        // 200 - ApiMessage is OK
        HttpStatus status =
            null == object ||
            ( object instanceof Optional && !(((Optional) object).isPresent()) ) ?
                HttpStatus.NOT_FOUND :
                    null != apiMessage && apiMessage.isERROR() ?
                        HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return ResponseEntity.status(status).body(object);
    }

    public static ResponseEntity<List<CreatedResourceResponse>>
        multipleResourcesResponse(ApiMessage apiMessage, String resourceURI) {

        // Getting ids were created
        List<ApiMessage> entityMessages =
            null == apiMessage.getAnything() ||
            !(apiMessage.getAnything() instanceof Collection<?>) ?
                Collections.emptyList() : (List<ApiMessage>) apiMessage.getAnything();

        if ( entityMessages.isEmpty() || apiMessage.isERROR() ) {

            ApiMessage singleMsg = new ApiMessage(AppMessage.E_RESOURCES_CREATION);
            CreatedResourceResponse response = new CreatedResourceResponse(singleMsg);

            return new ResponseEntity<>(
                Collections.singletonList(response), HttpStatus.BAD_REQUEST);
        }

        HttpStatus httpStatus = entityMessages.get(0).wasResourceCreated() ?
            HttpStatus.CREATED : HttpStatus.OK;

        List<CreatedResourceResponse> resources =
            entityMessages
                .stream()
                .map(entityMessage -> {
                    CreatedResourceResponse crr =
                        new CreatedResourceResponse(entityMessage);
                    crr.setUri(resourceURI);
                    return crr;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(resources, httpStatus);
    }

}