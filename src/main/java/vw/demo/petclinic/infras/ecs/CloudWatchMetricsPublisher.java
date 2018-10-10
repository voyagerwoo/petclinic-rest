package vw.demo.petclinic.infras.ecs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloudWatchMetricsPublisher {
    private final MetricsEndpoint metricsEndpoint;
    private final AmazonEcsMetadata amazonEcsMetadata;

    @Autowired
    public CloudWatchMetricsPublisher(MetricsEndpoint metricsEndpoint, AmazonEcsMetadata amazonEcsMetadata) {
        this.metricsEndpoint = metricsEndpoint;
        this.amazonEcsMetadata = amazonEcsMetadata;

    }

    @Scheduled(cron = "${spring.operational.metrics.cloudwatch.publish.cron:*/10 * * * * *}")
    public void publishMetrics(){
        log.info(amazonEcsMetadata.toString());
        metricsEndpoint.listNames().getNames().stream()
                .map(name -> metricsEndpoint.metric(name, null))
                .forEach(metric -> {
                    metric.getMeasurements().forEach(measurement -> {
                        log.info(metric.getName() + " | " +  measurement.getStatistic().toString() + " | " + measurement.getValue());
                    });
                });
    }

}
