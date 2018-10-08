package vw.demo.petclinic.interfaces.tests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test/stress")
@Slf4j
public class StressTestController {
    @GetMapping("")
    public ResponseEntity stress() {
        log.info("stress check");

        Double sum = 0d;
        for (int i = 0; i < 10000000; i++) {
            sum += Math.sqrt(i);
        }

        Double finalSum = sum;
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("result", "OK");
            put("sum", finalSum);
        }});
    }
}
