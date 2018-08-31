package vw.demo.petclinic.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vw.demo.petclinic.domains.vet.VetRepository;

@RestController
@RequestMapping("/open/vets")
@CrossOrigin("*")
public class OpenVetController {
    @Autowired
    private VetRepository vetRepository;

    @GetMapping
    public ResponseEntity getAllVets(){
        return ResponseEntity.ok().body(vetRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getVet(@PathVariable Integer id){
        return ResponseEntity.ok().body(vetRepository.findById(id));
    }
}
