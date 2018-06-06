package vw.demo.petclinic.domains.vet

import org.springframework.data.jpa.repository.JpaRepository

interface VetRepository extends JpaRepository<Vet, Integer> {
}
