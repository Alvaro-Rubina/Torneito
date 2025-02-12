package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConfederationResponseDTO {

    //
    private String name;
    private String logo;
    private List<CountryResponseDTO> countries;
    private List<TeamResponseDTO> teams;
}
