package vw.demo.petclinic.domains.vet

import vw.demo.petclinic.domains.models.NamedEntity

import javax.persistence.Entity
import javax.persistence.Table

@Entity(name='specialties')
class Specialty extends NamedEntity implements Serializable {
}
