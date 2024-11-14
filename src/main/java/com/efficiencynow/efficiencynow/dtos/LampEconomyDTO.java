package com.efficiencynow.efficiencynow.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar a economia de lâmpadas.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LampEconomyDTO {

    /**
     * Número de lâmpadas fluorescentes.
     * Deve ser maior ou igual a 0.
     */
    @Min(0)
    private Integer numberOfFluorescentLamps;

    /**
     * Número de lâmpadas incandescentes.
     * Deve ser maior ou igual a 0.
     */
    @Min(0)
    private Integer numberOfIncandescentLamps;

    /**
     * Número de lâmpadas halógenas.
     * Deve ser maior ou igual a 0.
     */
    @Min(0)
    private Integer numberOfHalogenLamps;
}