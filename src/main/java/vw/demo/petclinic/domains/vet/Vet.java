package vw.demo.petclinic.domains.vet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vw.demo.petclinic.domains.base.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Vet extends Person {
    @Column(nullable = false)
    private Long userId;
    @ManyToMany
    @JoinTable(name = "vet_specialties")
    private Set<Specialty> specialties;
    private Integer year;
    private String description;

    public Vet(@NotEmpty String firstName, @NotEmpty String lastName, Long userId, Set<Specialty> specialties, Integer year, String description) {
        super(firstName, lastName);
        this.userId = userId;
        this.specialties = specialties;
        this.year = year;
        this.description = description;
    }
}
