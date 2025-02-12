package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class CountryDTO {

    //
    private String name;
    private String flag;
    private String continent;
    private List<String> leagues;
    private List<String> teams;
}
