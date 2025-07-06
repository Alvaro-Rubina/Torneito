package org.alvarub.fulbitoapi.model.dto.confederation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConfederationDTO implements Serializable {

    //
    @Schema(example = "CONMEBOL") @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "https://url-del-logo") @NotBlank(message = "El url del logo es obligatorio")
    private String logo;
}
