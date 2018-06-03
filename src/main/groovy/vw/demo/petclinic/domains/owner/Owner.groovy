package vw.demo.petclinic.domains.owner

import vw.demo.petclinic.domains.models.Person

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name='owners')
class Owner extends Person{

}
