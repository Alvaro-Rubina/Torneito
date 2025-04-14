package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SeasonDTO implements Serializable {

    //
    @Schema(example = "ARG-2024") @NotBlank(message = "El codigo es obligatorio")
    private String code;

    @Schema(example = "1") @NotNull(message = "El año es obligatorio")
    private Long year;

    @Schema(example = "1", description = "ID de la liga a la que pertenece")
    @NotNull(message = "El ID de la liga es obligatorio")
    private Long leagueId;

    @Schema(description = "Lista de IDs de los equipos que participan en la temporada", example = "[1, 2, 3]")
    private List<Long> teamsIds;

}
