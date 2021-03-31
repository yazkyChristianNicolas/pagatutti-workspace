package ar.com.pagatutti.apicore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ar.com.pagatutti.apicorebeans.dto.MTMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "metadata")
@PropertySource(value = "classpath:beans-mapping/mt-metadata.properties")
public class MTMetadataConfig {

    private List<MTMetadata> masterTables = new ArrayList<>();

    public Optional<MTMetadata> findByTableName(String tableName) {
        return
            masterTables
                .stream()
                .filter(mt -> mt.getTable().equalsIgnoreCase(tableName))
                .findFirst();
    }

    public Optional<MTMetadata> findByEntity(String entity) {
        return
            masterTables
                .stream()
                .filter(mt -> mt.getEntity().equalsIgnoreCase(entity))
                .findFirst();
    }

}
