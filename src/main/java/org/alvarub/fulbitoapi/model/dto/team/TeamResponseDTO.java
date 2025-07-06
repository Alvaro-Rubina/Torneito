package org.alvarub.fulbitoapi.model.dto.team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.alvarub.fulbitoapi.model.dto.confederation.ConfederationResponseDTO;
import org.alvarub.fulbitoapi.model.dto.country.CountryResponseDTO;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TeamResponseDTO implements Serializable {

    @Schema(example = "1", description = "ID del equipo")
    private Long id;

    @Schema(example = "Boca Juniors", description = "Nombre del equipo")
    private String name;

    @Schema(example = "https://url-del-logo", description = "URL del logo (escudo) del equipo")
    private String logo;

    @Schema(description = "Confederación a la que pertenece el equipo")
    private ConfederationResponseDTO confederation;

    @Schema(description = "País al que pertenece el equipo")
    private CountryResponseDTO country;
}
