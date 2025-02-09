package org.alvarub.fulbitoapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Country {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nationalTeamName;
    private String flag;
    private String logo;

    @Enumerated(EnumType.STRING)
    private Continent continent;

    @OneToMany(mappedBy = "country")
    private List<League> leagues;

    @OneToMany(mappedBy = "country")
    private List<Team> teams;

    @ManyToOne
    @JoinColumn(name = "confederation_id")
    private Confederation confederation;

}