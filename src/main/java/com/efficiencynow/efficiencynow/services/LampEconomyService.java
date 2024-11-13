package com.efficiencynow.efficiencynow.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Serviço responsável por calcular a economia de energia ao substituir lâmpadas
 * fluorescentes, incandescentes e halógenas por lâmpadas de LED.
 */
@Service
public class LampEconomyService {

    // Potências das lâmpadas em watts
    private static final double LED_POWER = 10.0;
    private static final double FLUORESCENT_POWER = 15.0;
    private static final double INCANDESCENT_POWER = 60.0;
    private static final double HALOGEN_POWER = 42.0;

    /**
     * Calcula a economia percentual ao substituir lâmpadas fluorescentes por lâmpadas de LED.
     *
     * @param numberOfFluorescentLamps Número de lâmpadas fluorescentes.
     * @return Economia percentual.
     */
    public double calcFluorescentToLedEconomy(Integer numberOfFluorescentLamps) {
        if (numberOfFluorescentLamps == null || numberOfFluorescentLamps == 0) {
            return 0.0;
        }
        double previousConsumption = numberOfFluorescentLamps * FLUORESCENT_POWER;
        double currentConsumption = numberOfFluorescentLamps * LED_POWER;
        double economy = previousConsumption - currentConsumption;
        return BigDecimal.valueOf((economy / previousConsumption) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calcula a economia percentual ao substituir lâmpadas incandescentes por lâmpadas de LED.
     *
     * @param numberOfIncandescentLamps Número de lâmpadas incandescentes.
     * @return Economia percentual.
     */
    public double calcIncandescentToLedEconomy(Integer numberOfIncandescentLamps) {
        if (numberOfIncandescentLamps == null || numberOfIncandescentLamps == 0) {
            return 0.0;
        }
        double previousConsumption = numberOfIncandescentLamps * INCANDESCENT_POWER;
        double currentConsumption = numberOfIncandescentLamps * LED_POWER;
        double economy = previousConsumption - currentConsumption;
        return BigDecimal.valueOf((economy / previousConsumption) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calcula a economia percentual ao substituir lâmpadas halógenas por lâmpadas de LED.
     *
     * @param numberOfHalogenLamps Número de lâmpadas halógenas.
     * @return Economia percentual.
     */
    public double calcHalogenToLedEconomy(Integer numberOfHalogenLamps) {
        if (numberOfHalogenLamps == null || numberOfHalogenLamps == 0) {
            return 0.0;
        }
        double previousConsumption = numberOfHalogenLamps * HALOGEN_POWER;
        double currentConsumption = numberOfHalogenLamps * LED_POWER;
        double economy = previousConsumption - currentConsumption;
        return BigDecimal.valueOf((economy / previousConsumption) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calcula a economia percentual total ao substituir lâmpadas fluorescentes, incandescentes
     * e halógenas por lâmpadas de LED.
     *
     * @param fluorescent  Número de lâmpadas fluorescentes.
     * @param incandescent Número de lâmpadas incandescentes.
     * @param halogen      Número de lâmpadas halógenas.
     * @return Economia percentual total.
     */
    public double calculateTotalEconomy(Integer fluorescent, Integer incandescent, Integer halogen) {
        fluorescent = (fluorescent == null) ? 0 : fluorescent;
        incandescent = (incandescent == null) ? 0 : incandescent;
        halogen = (halogen == null) ? 0 : halogen;

        double fluorescentEconomy = calcFluorescentToLedEconomy(fluorescent);
        double incandescentEconomy = calcIncandescentToLedEconomy(incandescent);
        double halogenEconomy = calcHalogenToLedEconomy(halogen);

        // Soma ponderada pela quantidade de lâmpadas para evitar média incorreta
        int totalLamps = fluorescent + incandescent + halogen;
        if (totalLamps == 0) {
            return 0.0;
        }
        double totalEconomy = ((fluorescent * fluorescentEconomy) +
                (incandescent * incandescentEconomy) +
                (halogen * halogenEconomy)) / totalLamps;
        return BigDecimal.valueOf(totalEconomy)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
