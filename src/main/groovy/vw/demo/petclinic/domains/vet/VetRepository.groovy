package vw.demo.petclinic.domains.vet

import org.springframework.dao.DataAccessException
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface VetRepository extends Repository<Vet, Integer> {
    @Transactional(readOnly = true)
    Collection<Vet> findAll() throws DataAccessException
}
