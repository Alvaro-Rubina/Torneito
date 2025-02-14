package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class LeagueResponseDTO {

    //
    @Schema(example = "1", description = "ID de la liga")
    private Long id;

    @Schema(example = "Liga Profesional Argentina", description = "Nombre de la liga")
    private String name;

    @Schema(example = "https://url-del-logo", description = "URL del logo de la liga")
    private String logo;

    @Schema(description = "Lista de temporadas asociadas a la liga")
    private List<SeasonResponseDTO> seasons;
}
