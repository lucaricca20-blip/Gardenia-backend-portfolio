package com.betacom.pr.dto.inputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressReq {

    private Integer id;
    private String country;
    private String city;
    private Integer postalCode;
    private String street;
    private Integer number;
}
