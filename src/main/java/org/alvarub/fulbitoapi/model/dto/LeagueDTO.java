package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class LeagueDTO implements Serializable {

    //
    @Schema(example = "Liga Profesional Argentina") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-del-logo")
    @NotBlank(message = "El url del logo es obligatorio")
    private String logo;

    @Schema(example = "1", description = "ID del país al que pertenece")
    @NotNull(message = "El ID del pais es obligatorio")
    private Long countryId;
}
