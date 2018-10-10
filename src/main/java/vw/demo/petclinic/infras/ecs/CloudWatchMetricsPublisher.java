package vw.demo.petclinic.infras.ecs;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CloudWatchMetricsPublisher {
    @Value("${amazon.cloudwatch.metric.namespace:ECS_CONTAINER_METRICS}")
    private String nameSpace;
    @Value("${amazon.region:ap-southeast-1}")
    private String region;

    private MetricsEndpoint metricsEndpoint;
    private AmazonEcsMetadata amazonEcsMetadata;

    private List<Dimension> dimensions;

    @Autowired
    public CloudWatchMetricsPublisher(MetricsEndpoint metricsEndpoint, AmazonEcsMetadata amazonEcsMetadata) {
        this.metricsEndpoint = metricsEndpoint;
        this.amazonEcsMetadata = amazonEcsMetadata;

        if (amazonEcsMetadata.isExist) {
            this.dimensions = new ArrayList<>();
            this.dimensions.add(new Dimension().withName("CLUSTER_NAME")
                    .withValue(amazonEcsMetadata.getCluster()));
            this.dimensions.add(new Dimension().withName("CONTAINER_INSTANCE_ID")
                    .withValue(amazonEcsMetadata.getContainerInstanceId()));
            this.dimensions.add(new Dimension().withName("TASK_DEFINITION")
                    .withValue(amazonEcsMetadata.getTaskDefinition()));
            this.dimensions.add(new Dimension().withName("CONTAINER_ID")
                    .withValue(amazonEcsMetadata.getContainerID()));
        }
    }

    @Scheduled(cron = "${spring.operational.metrics.cloudwatch.publish.cron:*/10 * * * * *}")
    public void publishMetrics() {
        if (amazonEcsMetadata.isExist) {
            metricsEndpoint.listNames().getNames().stream()
                    .map(name -> metricsEndpoint.metric(name, null))
                    .map(metric -> metric.getMeasurements().stream().map(measurement -> new MetricDatum()
                            .withMetricName(metric.getName() + "." + measurement.getStatistic().toString())
                            .withDimensions(dimensions)
                            .withUnit(StandardUnit.None)
                            .withValue(measurement.getValue())))
                    .flatMap(s -> s).forEach(this::publish);
        }
    }


    private void publish(MetricDatum datum) {
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(nameSpace)
                .withMetricData(datum);

        PutMetricDataResult response = AmazonCloudWatchClientBuilder
                .standard()
                .withRegion(region)
                .build().putMetricData(request);

        log.info(response.toString());

    }
}
