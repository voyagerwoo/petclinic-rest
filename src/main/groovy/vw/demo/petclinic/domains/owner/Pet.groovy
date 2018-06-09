package vw.demo.petclinic.domains.owner

import vw.demo.petclinic.domains.models.NamedEntity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name='owners')
class Pet extends NamedEntity{


}
