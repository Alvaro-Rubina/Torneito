package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class CountryDTO {

    //
    @Schema(example = "Argentina") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-de-la-bandera")
    @NotBlank(message = "El url de la bandera es obligatorio")
    private String flag;

    @Schema(example = "America") @NotBlank(message = "El continente es obligatorio")
    private String continent;

    @Schema(example = "Conmebol")
    @NotBlank(message = "El nombre de la confederacion es obligatorio")
    private String confederationName;

    @Schema(example = "[\"Liga Profesional Argentina\", \"Primera Nacional Argentina\"]") @NotEmpty(message = "La lista de ligas no puede estar vacía")
    @Size(min = 1, message = "Debe haber al menos una liga en la lista")
    private List<String> leagues;

    @Schema(example = "[\"Boca Juniors\", \"Deportivo Maipu\"]") @NotEmpty(message = "La lista de equipos no puede estar vacía")
    @Size(min = 1, message = "Debe haber al menos un equipo en la lista")
    private List<String> teams;
}
