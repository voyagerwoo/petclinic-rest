package vw.demo.petclinic.interfaces

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vw.demo.petclinic.domains.vet.VetRepository

@RestController
@RequestMapping("vets")
@CrossOrigin("*")
class VetController {
    @Autowired
    VetRepository vetRepository

    @GetMapping
    ResponseEntity getAllVets(){
        return ResponseEntity.ok().body(vetRepository.findAll())
    }

    @GetMapping("/{id}")
    ResponseEntity getVet(@PathVariable Integer id){
        return ResponseEntity.ok().body(vetRepository.findById(id))
    }
}
