package vw.demo.petclinic.infras.ecs;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloudWatchMetricsPublisher {
    @Value("${petclinic.metric.namespace:ECS_CONTAINER_METRICS}")
    private String nameSpace;

    private MetricsEndpoint metricsEndpoint;
    private AmazonEcsMetadata amazonEcsMetadata;

    private Dimension clusterDimension;
    private Dimension containerInstanceIdDimension;
    private Dimension taskDefinitionDimension;
    private Dimension containerIdDimension;

    @Autowired
    public CloudWatchMetricsPublisher(MetricsEndpoint metricsEndpoint, AmazonEcsMetadata amazonEcsMetadata) {
        this.metricsEndpoint = metricsEndpoint;
        this.amazonEcsMetadata = amazonEcsMetadata;

        this.clusterDimension = new Dimension().withName("CLUSTER_NAME")
                .withValue(amazonEcsMetadata.getCluster());
        this.containerInstanceIdDimension = new Dimension().withName("CONTAINER_INSTANCE_ID")
                .withValue(amazonEcsMetadata.getContainerInstanceId());
        this.taskDefinitionDimension = new Dimension().withName("TASK_DEFINITION")
                .withValue(amazonEcsMetadata.getTaskDefinition());
        this.containerIdDimension = new Dimension().withName("CONTAINER_ID")
                .withValue(amazonEcsMetadata.getContainerID());
    }

    @Scheduled(cron = "${spring.operational.metrics.cloudwatch.publish.cron:*/10 * * * * *}")
    public void publishMetrics() {
        if(amazonEcsMetadata.isExist) {
            metricsEndpoint.listNames().getNames().stream()
                    .map(name -> metricsEndpoint.metric(name, null))
                    .map(metric -> metric.getMeasurements().stream().map(measurement -> {
                        log.info(metric.getName() + " | " + measurement.getStatistic().toString() + " | " + measurement.getValue());
                        return new MetricDatum().withMetricName(metric.getName() + "." + measurement.getStatistic().toString())
                                .withUnit(StandardUnit.None)
                                .withValue(measurement.getValue())
                                .withDimensions(clusterDimension, containerIdDimension,
                                        containerInstanceIdDimension, taskDefinitionDimension);
                    })).flatMap(s -> s).forEach(this::publish);
        }
    }


    private void publish(MetricDatum datum) {
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(nameSpace)
                .withMetricData(datum);

        PutMetricDataResult response = AmazonCloudWatchClientBuilder
                .standard()
                .build().putMetricData(request);

    }
}
