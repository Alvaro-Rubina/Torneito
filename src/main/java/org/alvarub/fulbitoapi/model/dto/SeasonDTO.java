package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SeasonDTO {

    //
    @Schema(example = "ARG-2024") @NotBlank(message = "El codigo es obligatorio")
    private String code;

    @Schema(example = "1") @NotNull(message = "El año es obligatorio")
    private Long year;

    @Schema(example = "[\"Boca Juniors\", \"RiBer Plate\"]") @NotEmpty(message = "La lista de equipos no puede estar vacía")
    private List<String> teams;

    @Schema(example = "Argentina")
    @NotBlank(message = "El nombre del país es obligatorio")
    private String countrieName;
}
