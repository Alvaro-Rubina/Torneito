package org.alvarub.fulbitoapi.model.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "confederation_id")
    private Confederation confederation;

    @OneToMany(mappedBy = "country")
    private List<League> leagues;

    @OneToMany(mappedBy = "country")
    private List<Team> teams;

}