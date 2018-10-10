package vw.demo.petclinic.infras.ecs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class AmazonEcsMetadata {
    boolean isExist=true;
    String cluster;
    String containerInstanceARN;
    String taskARN;
    String taskDefinitionFamily;
    String taskDefinitionRevision;
    String containerID;
    String containerName;
    String dockerContainerName;
    String imageID;
    String imageName;
    List<PortMapping> portMappings;
    List<Network> networks;
    String metadataFileStatus;


    public AmazonEcsMetadata(Boolean isExist) {
        this.isExist = isExist;
    }

    public String getTaskDefinition() {
        return taskDefinitionFamily + ":" + taskDefinitionRevision;
    }

    public String getContainerInstanceId() {
        String[] splited = containerInstanceARN.split("/");
        if (splited.length == 2)
            return splited[1];
        else
            return containerInstanceARN;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class PortMapping {
        Integer containerPort;
        Integer hostPort;
        String bindIp;
        String protocol;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Network {
        String networkMode;
        @JsonProperty("IPv4Addresses")
        List<String> ipv4Addresses;
    }
}