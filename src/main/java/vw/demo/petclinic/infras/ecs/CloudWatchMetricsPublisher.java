package vw.demo.petclinic.infras.ecs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CloudWatchMetricsPublisher {

    @Resource
    private MetricsEndpoint metricsEndpoint;

    @Resource
    private AmazonEcsMetadata amazonEcsMetadata;

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
        throw new RuntimeException();
    }

    @Getter
    @AllArgsConstructor
    public static class MetricValue {
        String name;
        String statistic;
        Double value;
    }
}
