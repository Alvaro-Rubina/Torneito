package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class CountryResponseDTO {

    //
    private String name;
    private String flag;
    private String continent;
    private List<LeagueResponseDTO> leagues;
    private List<TeamResponseDTO> teams;

}
