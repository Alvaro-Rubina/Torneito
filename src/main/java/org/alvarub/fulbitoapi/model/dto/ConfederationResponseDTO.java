package org.alvarub.fulbitoapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConfederationResponseDTO implements Serializable {

    //
    @Schema(example = "1", description = "ID de la confederacion")
    private Long id;

    @Schema(example = "CONMEBOL", description = "Nombre de la confederacion")
    private String name;

    @Schema(example = "https://url-del-logo", description = "URL del logo de la confederacion")
    private String logo;
}
