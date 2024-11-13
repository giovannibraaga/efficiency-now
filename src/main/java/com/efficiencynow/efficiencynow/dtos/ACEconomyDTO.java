package com.efficiencynow.efficiencynow.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ACEconomyDTO é uma classe que representa os dados de economia de ar-condicionado.
 * Contém informações sobre a quantidade de aparelhos, potência em BTUs, temperatura ajustada,
 * horas de uso diário e custo médio por kWh.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ACEconomyDTO {

    /**
     * Quantidade de aparelhos de ar-condicionado.
     * Deve ser um valor maior ou igual a 0.
     */
    @Min(0)
    private int numberOfAirConditioners;

    /**
     * Potência do ar-condicionado em BTUs.
     * Deve ser um valor maior ou igual a 7500.
     */
    @Min(7500)
    private int btu;

    /**
     * Temperatura ajustada pelo usuário.
     * Deve ser um valor entre 16°C e 30°C.
     */
    @Min(16)
    @Max(30)
    private double temperature;

    /**
     * Horas de uso diário do ar-condicionado.
     * Deve ser um valor maior ou igual a 1.
     */
    @Min(1)
    private int hoursPerDay;

    /**
     * Custo médio por kWh em moeda local.
     * Opcional, deve ser um valor maior ou igual a 0.
     */
    @Min(0)
    private double costPerKWh;
}