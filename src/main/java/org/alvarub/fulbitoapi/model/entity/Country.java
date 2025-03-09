package org.alvarub.fulbitoapi.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class Country {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String flag;

    @Enumerated(EnumType.STRING)
    private Continent continent;

    private String confederationName;

    @OneToMany
    private List<League> leagues;

    @OneToMany
    private List<Team> teams;

}