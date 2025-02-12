package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SeasonResponseDTO {

    //
    private String code; // Ejemplo: ARG-2021
    private Long year;
    private List<TeamResponseDTO> teams;
}
