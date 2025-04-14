package org.alvarub.fulbitoapi.model.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class League {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}