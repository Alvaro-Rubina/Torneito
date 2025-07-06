package org.alvarub.fulbitoapi.model.dto.league;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.alvarub.fulbitoapi.model.dto.country.CountryResponseDTO;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class LeagueResponseDTO implements Serializable {

    //
    @Schema(example = "1", description = "ID de la liga")
    private Long id;

    @Schema(example = "Liga Profesional Argentina", description = "Nombre de la liga")
    private String name;

    @Schema(example = "https://url-del-logo", description = "URL del logo de la liga")
    private String logo;

    @Schema(description = "País al que pertenece la liga")
    private CountryResponseDTO country;
}
