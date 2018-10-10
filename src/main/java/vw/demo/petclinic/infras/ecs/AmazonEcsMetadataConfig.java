package vw.demo.petclinic.infras.ecs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class AmazonEcsMetadataConfig {
    @Value("${ECS_CONTAINER_METADATA_FILE:}")
    private String ecsContainerMetadataFilePath;

    private ObjectMapper mapper = getObjectMapper();

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public AmazonEcsMetadata amazonEcsMetadata() {
        if(StringUtils.isEmpty(ecsContainerMetadataFilePath)) {
            log.warn("There is no $ECS_CONTAINER_METADATA_FILE.");
            return new AmazonEcsMetadata(false);
        }
        try {
            String metaFileContent = Files.readAllLines(Paths.get(ecsContainerMetadataFilePath)).stream()
                    .collect(Collectors.joining("\n"));
            return mapper.readValue(metaFileContent, AmazonEcsMetadata.class);
        } catch (IOException e){
            log.warn("There is no ECS container meta file.", e);
            return new AmazonEcsMetadata(false);
        }
    }
}
