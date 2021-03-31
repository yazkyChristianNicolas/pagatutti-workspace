package ar.com.pagatutti.apicore.controllers;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import ar.com.pagatutti.apicore.services.MasterTableService;
import ar.com.pagatutti.apicorebeans.api.controllers.MasterTableControllerInterface;
import ar.com.pagatutti.apicorebeans.api.request.MTRowRequest;
import ar.com.pagatutti.apicorebeans.api.response.BaseApiResponse;
import ar.com.pagatutti.apicorebeans.api.response.MTDataResponse;
import ar.com.pagatutti.apicorebeans.api.response.MTTypesResponse;
import ar.com.pagatutti.apicorebeans.dto.ApiMessage;
import ar.com.pagatutti.apicorebeans.enums.AppMessage;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
    path = "/admin/master-tables",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class MasterTableController implements MasterTableControllerInterface{

    @Autowired
    private MasterTableService masterTableService;

    @GetMapping
    public List<String> getAvailableTables() {
        return masterTableService.getAvailableTables();
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse>
        saveMasterTable(@RequestBody MTRowRequest mtRowRequest) {

        if ( StringUtils.isEmpty(mtRowRequest.getTable()) ||
             MapUtils.isEmpty(mtRowRequest.getContent()) ) {
            return ResponseEntity
                    .ok(new BaseApiResponse(AppMessage.E_INCOMPLETE_CONTENT));
        }

        ApiMessage result = masterTableService.addNewRegister(mtRowRequest);
        BaseApiResponse response = new BaseApiResponse(result);
        HttpStatus httpStatus = result.isOK() ? HttpStatus.CREATED : HttpStatus.OK;

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseApiResponse>
        editMasterTable(@RequestBody MTRowRequest mtRowRequest,
                        @PathVariable Long id) {

        ApiMessage result =
            StringUtils.isEmpty(mtRowRequest.getTable()) ||
                MapUtils.isEmpty(mtRowRequest.getContent()) ?
                    new ApiMessage(AppMessage.E_INCOMPLETE_CONTENT) :
                    masterTableService.editRegister(id, mtRowRequest);

        return ResponseEntity.ok(new BaseApiResponse(result));
    }

    @GetMapping(path = "/metadata")
    public MTTypesResponse
        getMetadataForTable(@RequestParam(name = "table") String table) {

        MTTypesResponse response = new MTTypesResponse();

        Map<String, String> columns = masterTableService.getMetadataForTable(table);

        if ( MapUtils.isEmpty(columns) ) {
            ApiMessage apiMsg = new ApiMessage(AppMessage.E_MT_MD_NOT_FOUND);
            apiMsg.formatMessage(table);
            response.setResult(apiMsg);
            return response;
        }

        response.setTable(table);
        response.setColumns(columns);

        return response;
    }

    @GetMapping(path = "/data")
    public MTDataResponse getDataForTable(
        @RequestParam(name = "table") String table) {

        MTDataResponse response = new MTDataResponse();

        Map<String, String> columns = masterTableService.getMetadataForTable(table);
        List<Object> data = masterTableService.getDataForTable(table);

        response.setTable(table);
        response.setColumns(columns);
        response.setData(data);

        return response;
    }

}
