package vw.demo.petclinic.interfaces.tests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/test/stress")
@Slf4j
public class StressTestController {
    private ExecutorService es = Executors.newFixedThreadPool(10);

    @GetMapping("")
    public ResponseEntity stress() {
        log.info("stress check");
        for (int i = 0; i < 10; i++) {
            es.submit(this::calculateSqrt);
        }
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("result", "OK");
            put("sum", calculateSqrt());
        }});
    }

    private Double calculateSqrt() {
        Double sum = 0d;
        for (int i = 0; i < 10000000; i++) {
            sum += Math.sqrt(i);
        }
        log.info("Calculate Result : " + sum);

        return sum;
    }
}
