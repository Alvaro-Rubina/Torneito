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
public class ConfederationDTO   {

    //
    @Schema(example = "CONMEBOL") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-del-logo") @NotBlank(message = "El url del logo es obligatorio")
    private String logo;

    @Schema(example = "[\"Argentina\", \"Brasil\"]") @NotEmpty(message = "La lista de paises no puede estar vacía")
    @Size(min = 1, message = "Debe haber al menos un pais en la lista")
    private List<String> countries;

    @Schema(example = "[\"Boca Juniors\", \"Colo Colo\"]") @NotEmpty(message = "La lista de equipos no puede estar vacía")
    @Size(min = 1, message = "Debe haber al menos un equipo en la lista")
    private List<String> teams;
}
