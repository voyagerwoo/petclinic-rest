package vw.demo.petclinic.domains.vet

import vw.demo.petclinic.domains.models.Person

import javax.persistence.Entity
import javax.persistence.JoinTable
import javax.persistence.OneToMany

@Entity(name='vets')
class Vet extends Person {
    @OneToMany
    @JoinTable(name = "vet_specialties")
    Set<Specialty> specialties
    Integer year
    String description

    Vet(Set<Specialty> specialties, Integer year, String description) {
        this.specialties = specialties
        this.year = year
        this.description = description
    }

    Vet() {
    }

    Set<Specialty> getSpecialties() {
        return specialties
    }

    void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties
    }

    Integer getYear() {
        return year
    }

    void setYear(Integer year) {
        this.year = year
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}
