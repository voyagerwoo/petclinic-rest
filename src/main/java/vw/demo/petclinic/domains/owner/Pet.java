package vw.demo.petclinic.domains.owner;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vw.demo.petclinic.domains.base.NamedEntity;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Pet extends NamedEntity{
    private int age;
}
