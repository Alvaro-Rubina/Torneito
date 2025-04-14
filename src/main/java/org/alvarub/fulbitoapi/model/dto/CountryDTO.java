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
public class CountryDTO implements Serializable {

    //
    @Schema(example = "Argentina") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-de-la-bandera")
    @NotBlank(message = "El url de la bandera es obligatorio")
    private String flag;

    @Schema(example = "America") @NotBlank(message = "El continente es obligatorio")
    private String continent;

    @Schema(example = "1", description = "ID de la confederacion")
    @NotNull(message = "El ID de la confederacion es obligatorio")
    private Long confederationId;

}
