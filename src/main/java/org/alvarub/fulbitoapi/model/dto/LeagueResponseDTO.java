package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class LeagueResponseDTO {

    //
    private String name;
    private String logo;
    private List<SeasonResponseDTO> seasons;
}
