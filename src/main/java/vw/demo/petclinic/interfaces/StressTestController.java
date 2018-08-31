package vw.demo.petclinic.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/open")
@Profile("stress")
@Slf4j
public class StressTestController {
    @GetMapping("/stress")
    public ResponseEntity stress() {
        log.info("stress check");

        Double sum = 0d;
        for (int i = 0; i < 10000000; i++) {
            sum += Math.sqrt(i);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", "OK");
        map.put("sum", sum);

        return ResponseEntity.ok(map);
    }
}
