package com.efficiencynow.efficiencynow.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Serviço para cálculo de consumo e economia de energia de aparelhos de ar condicionado.
 */
@Service
public class ACEconomyService {
    // Consumo medio mensal em kwh para ar condicionados de 7500, 9000, 12000 e 18000 btus.
    private static final double CONSUMPTION_7500_BTU = 15.7;
    private static final double CONSUMPTION_9000_BTU = 17.1;
    private static final double CONSUMPTION_12000_BTU = 22.7;
    private static final double CONSUMPTION_18000_BTU = 38.6;

    // Percentuais de aumento no consumo fora da faixa ideal 4% para cada grau, tanto acima quanto abaixo.
    private static final double PERCENT_INCREASE_PER_DEGREE = 0.04;

    // Custo medio do kwh no Brasil.
    private static final double COST_PER_KWH = 0.74;

    /**
     * Obtém o consumo mensal médio em kWh com base no valor de BTU fornecido.
     *
     * @param btu Valor de BTU do ar condicionado.
     * @return Consumo mensal médio em kWh.
     * @throws IllegalArgumentException Se o valor de BTU fornecido não for suportado.
     */
    private double getMonthlyConsumptionByBTU(int btu) {
        switch (btu) {
            case 7500:
                return CONSUMPTION_7500_BTU;
            case 9000:
                return CONSUMPTION_9000_BTU;
            case 12000:
                return CONSUMPTION_12000_BTU;
            case 18000:
                return CONSUMPTION_18000_BTU;
            default:
                throw new IllegalArgumentException("O valor de BTU fornecido não é suportado. Use 7500, 9000, 12000 ou 18000 BTUs.");

        }
    }

    /**
     * Calcula o consumo mensal em kWh com base no número de aparelhos de ar condicionado,
     * no valor de BTU e na temperatura fornecida.
     *
     * @param numberOfAirConditioners Quantidade de aparelhos de ar condicionado.
     * @param btu                     Valor de BTU do ar condicionado.
     * @param temperature             Temperatura em graus Celsius.
     * @return Consumo mensal em kWh.
     */
    public double calcMonthlyConsumption(int numberOfAirConditioners, int btu, double temperature) {
        double baseConsumption = getMonthlyConsumptionByBTU(btu);

        double adjustmentFactor = 1.0;
        if (temperature < 22) {
            adjustmentFactor += (22 - temperature) * PERCENT_INCREASE_PER_DEGREE;
        } else if (temperature > 24) {
            adjustmentFactor += (temperature - 24) * PERCENT_INCREASE_PER_DEGREE;
        }

        return BigDecimal.valueOf(numberOfAirConditioners * baseConsumption * adjustmentFactor * COST_PER_KWH)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calcula o consumo ideal mensal em kWh (temperatura ajustada entre 22-24°C).
     *
     * @param numberOfAirConditioners Quantidade de aparelhos.
     * @param btu                     Potência em BTUs.
     * @return Consumo ideal mensal em kWh.
     */
    public double calcIdealMonthlyConsumption(int numberOfAirConditioners, int btu) {
        double baseConsumption = getMonthlyConsumptionByBTU(btu);

        double totalConsumption = numberOfAirConditioners * baseConsumption;
        return BigDecimal.valueOf(totalConsumption * COST_PER_KWH)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calcula a porcentagem de economia mensal ao ajustar para a faixa ideal.
     *
     * @param currentMonthlyConsumption Consumo mensal atual em kWh.
     * @param idealMonthlyConsumption   Consumo mensal ideal em kWh.
     * @return Porcentagem de economia mensal.
     */
    public double calcMonthlyEconomyPercentage(double currentMonthlyConsumption, double idealMonthlyConsumption) {
        double economy = currentMonthlyConsumption - idealMonthlyConsumption;

        return BigDecimal.valueOf((economy / currentMonthlyConsumption) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
