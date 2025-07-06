package org.alvarub.fulbitoapi.model.dto.team;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TeamDTO implements Serializable {

    @Schema(example = "Boca Juniors") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-del-escudo")
    @NotBlank(message = "El url del logo (escudo) es obligatorio")
    private String logo;

    @Schema(example = "1", description = "ID de la confederación a la que pertenece")
    private Long confederationId;

    @Schema(example = "1", description = "ID del país al que pertenece")
    private Long countryId;
}
