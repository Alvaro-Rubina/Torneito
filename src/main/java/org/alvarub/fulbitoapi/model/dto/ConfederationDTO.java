package org.alvarub.fulbitoapi.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConfederationDTO   {

    //
    private String name;
    private String logo;
    private List<String> countries;
    private List<String> teams;
}
