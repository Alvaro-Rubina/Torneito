package org.alvarub.fulbitoapi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.alvarub.fulbitoapi.model.enums.Continent;

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
}