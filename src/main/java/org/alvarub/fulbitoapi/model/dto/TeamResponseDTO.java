package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String logo;
}
