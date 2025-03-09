package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TeamDTO {

    @Schema(example = "Boca Juniors") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-del-escudo")
    @NotBlank(message = "El url del logo (escudo) es obligatorio")
    private String logo;

    @Schema(example = "Argentina")
    @NotBlank(message = "El nombre del país es obligatorio")
    private String countrieName;
}
