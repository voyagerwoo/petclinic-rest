package vw.demo.petclinic.interfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/petclinic")
@CrossOrigin("*")
@RefreshScope
class OpenPetClinicController {
    @Value("${petclinic.name}")
    private String petClinicName;

    @GetMapping("/name")
    ResponseEntity getName(){
        return ResponseEntity.ok(petClinicName);
    }
}
