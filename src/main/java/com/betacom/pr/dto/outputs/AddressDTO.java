package com.betacom.pr.dto.outputs;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class AddressDTO {

    private Integer id;
    private String country;
    private String city;
    private Integer postalCode;
    private String street;
    private Integer number;
}
