package vw.demo.petclinic.domains.vet;

import lombok.Getter;
import vw.demo.petclinic.domains.base.NamedEntity;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
class Specialty extends NamedEntity implements Serializable {
}
