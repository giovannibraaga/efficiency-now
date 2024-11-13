package com.efficiencynow.efficiencynow.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LampEconomyDTO {

    @Min(0)
    private Integer numberOfFluorescentLamps;

    @Min(0)
    private Integer numberOfIncandescentLamps;

    @Min(0)
    private Integer numberOfHalogenLamps;
}