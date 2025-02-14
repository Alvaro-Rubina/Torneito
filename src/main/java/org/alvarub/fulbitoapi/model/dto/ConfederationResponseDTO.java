package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConfederationResponseDTO {

    //
    @Schema(example = "1", description = "ID de la confederacion")
    private Long id;

    @Schema(example = "CONMEBOL", description = "Nombre de la confederacion")
    private String name;

    @Schema(example = "https://url-del-logo", description = "URL del logo de la confederacion")
    private String logo;

    @Schema(description = "Paises asociados a la confederacion")
    private List<CountryResponseDTO> countries;

    @Schema(description = "Equipos asociados a la confederacion")
    private List<TeamResponseDTO> teams;
}
