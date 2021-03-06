package ar.com.pagatutti.apicore.services;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ar.com.pagatutti.apicore.config.MTMetadataConfig;
import ar.com.pagatutti.apicore.helpers.BeansHelper;
import ar.com.pagatutti.apicorebeans.api.request.MTRowRequest;
import ar.com.pagatutti.apicorebeans.dto.ApiMessage;
import ar.com.pagatutti.apicorebeans.dto.MTMetadata;
import ar.com.pagatutti.apicorebeans.enums.AppMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MasterTableService {

    private static final Logger LOG = LoggerFactory.getLogger(MasterTableService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MTMetadataConfig mtMetadataConfig;

    public List<String> getAvailableTables() {
        return
            mtMetadataConfig
                .getMasterTables()
                .stream()
                .map(MTMetadata::getTable)
                .collect(Collectors.toList());
    }

    public Map<String, String> getMetadataForTable(String tableName) {

        Map<String, String> tableMetadata = new HashMap<>();

        // Retrieve metadata based on table name
        Optional<MTMetadata> optMetadata =
            mtMetadataConfig.findByTableName(tableName);

        if ( !optMetadata.isPresent() )
            return tableMetadata;

        MTMetadata metadata = optMetadata.get();

        try {
            // Creating model of Entity class to get its properties and types
            Class beanClass = Class.forName(metadata.getEntity());
            tableMetadata =
                BeansHelper.extractColumnsFromEntity(beanClass.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOG.error(AppMessage.E_WRONG_MT_METADATA.getMessage(), tableName, e);
        }

        return tableMetadata;
    }

    public List<Object> getDataForTable(String tableName) {

        List<Object> result = new ArrayList<>();

        // Retrieve metadata based on table name
        Optional<MTMetadata> optMetadata =
            mtMetadataConfig.findByTableName(tableName);

        if ( !optMetadata.isPresent() )
            return result;

        MTMetadata metadata = optMetadata.get();

        try {
            // Creating model of Entity class to get their properties and types
            Class beanClass = Class.forName(metadata.getRepository());
            JpaRepository repository = (JpaRepository) context.getBean(beanClass);
            return repository.findAll();
        } catch (ClassNotFoundException e) {
            LOG.error(AppMessage.E_WRONG_MT_METADATA.getMessage(), tableName, e);
        }

        return result;
    }

    public ApiMessage addNewRegister(MTRowRequest mtRowRequest) {
        return handleMTObject(mtRowRequest, null);
    }

    public ApiMessage editRegister(Long id, MTRowRequest mtRowRequest) {
        return handleMTObject(mtRowRequest, id);
    }

    private ApiMessage handleMTObject(MTRowRequest mtRowRequest, Long id) {

        ApiMessage apiMessage;
        String tableName = mtRowRequest.getTable();

        // Retrieve metadata based on table name
        Optional<MTMetadata> optMetadata =
            mtMetadataConfig.findByTableName(tableName);

        // Notify if master table is not present
        if ( !optMetadata.isPresent() ) {
            ApiMessage apiMsg = new ApiMessage(AppMessage.E_MT_NOT_FOUND);
            LOG.warn(apiMsg.formatMessage(tableName));
            return apiMsg;
        }

        MTMetadata metadata = optMetadata.get();

        try {
            // Translating metadata to objects
            Class entityClass =
                Class.forName(metadata.getEntity());
            Class repositoryClass =
                Class.forName(metadata.getRepository());

            // Getting entity repository
            JpaRepository repository =
                (JpaRepository) context.getBean(repositoryClass);

            Object object;

            // When id is specified, it's an add operation else it's an edit operation
            if ( null != id ) {

                Optional<Object> optObject = repository.findById(id);

                if ( !optObject.isPresent() ) {
                    ApiMessage apiMsg = new ApiMessage(AppMessage.E_MT_PK_NOT_FOUND);
                    LOG.error(apiMsg.formatMessage(tableName));
                    return apiMsg;
                }

                object = optObject.get();
            } else {
                object = entityClass.newInstance();
            }

            // Extract data to persist
            Map<String, String> receivedData = mtRowRequest.getContent();

            // Extract entity properties (table columns) as Map
            // considering column name instead of property name.
            Map<String, String> entityColumns =
                BeansHelper.translateColumnsForEntity(object);

            // This list will store all keys not included in request
            // to be ignored at processing
            List<String> columnsToBeIgnored = new ArrayList<>();

            // Update entity columns based on data received in request
            for ( Map.Entry<String, String> column :
                    entityColumns.entrySet() ) {

                // "Column name" has precedence over "Property name"
                // as key to be modified.
                String keyToModify = column.getValue();

                // if key is not specified in request, it will be skipped
                if ( !receivedData.containsKey(keyToModify) ) {
                    columnsToBeIgnored.add(column.getKey());
                    continue;
                }

                // Beans are handle by "property name" instead of "column name"
                // so, entityColumns map is overwritten based on original
                // property name and value received in request
                entityColumns.put(column.getKey(), receivedData.get(keyToModify));
            }

            // Removing ignored keys
            columnsToBeIgnored.forEach(entityColumns::remove);

            // Check if there are properties to persist
            if ( MapUtils.isEmpty(entityColumns) )
                return new ApiMessage(AppMessage.E_DATA_NOT_FOUND);

            // Property values are copied to entity instance
            BeansHelper.populate(object, entityColumns);

            // Entity is persisted to database
            repository.save(object);

            apiMessage = new ApiMessage(AppMessage.OK);

        } catch ( ClassNotFoundException |
                  IllegalAccessException |
                  InstantiationException e ) {
            apiMessage = new ApiMessage(AppMessage.E_WRONG_MT_METADATA);
            LOG.error(apiMessage.formatMessage(tableName), e);
        } catch (InvocationTargetException e) {
            apiMessage = new ApiMessage(AppMessage.E_POPULATING_MT);
            LOG.error(apiMessage.formatMessage(tableName), e);
        }

        return apiMessage;
    }


}