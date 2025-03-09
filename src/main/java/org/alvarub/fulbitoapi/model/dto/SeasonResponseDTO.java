package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SeasonResponseDTO {

    //
    @Schema(example = "1", description = "ID de la temporada")
    private Long id;

    @Schema(example = "ARG-2024", description = "Codigo de la temporada")
    private String code; // Ejemplo: ARG-2024

    @Schema(example = "2024", description = "Año de la temporada")
    private Long year;

    @Schema(example = "Argentina")
    private String countrieName;

    @Schema(description = "Lista de equipos presentes en la temporada")
    private List<TeamResponseDTO> teams;
}
