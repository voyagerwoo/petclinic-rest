package vw.demo.petclinic.interfaces

import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/test')
@Slf4j
class TestController {
    @GetMapping("/stress")
    ResponseEntity stress() {
        log.info("stress check")

        Double sum = 0d
        for (int i = 0; i < 1000000; i++) {
            sum += Math.sqrt(i)
        }

        Map<String, Object> map = new HashMap<>()
        map.put("result", "OK")
        map.put("sum", sum)

        return ResponseEntity.ok(map)
    }
}
