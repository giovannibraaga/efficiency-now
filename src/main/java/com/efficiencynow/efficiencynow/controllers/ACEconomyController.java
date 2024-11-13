package com.efficiencynow.efficiencynow.controllers;

import com.efficiencynow.efficiencynow.dtos.ACEconomyDTO;
import com.efficiencynow.efficiencynow.services.ACEconomyService;
import com.efficiencynow.efficiencynow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por gerenciar as operações relacionadas à economia de energia de ar-condicionado.
 */
@RestController
@RequestMapping("/ac-economy")
public class ACEconomyController {

    @Autowired
    private ACEconomyService acEconomyService;

    @Autowired
    private UserService userService;

    /**
     * Calcula a economia mensal de energia com base nos dados fornecidos.
     *
     * @param sessionToken Token de sessão do usuário, obtido do cookie.
     * @param economyDTO   Dados de entrada para o cálculo da economia.
     * @return ResponseEntity com a porcentagem de economia mensal ou status 401 se não autenticado.
     */
    @PostMapping("/calc-monthly-economy")
    public ResponseEntity<Double> calcMonthlyEconomy(@CookieValue(name = "SESSION", required = false) String sessionToken,
                                                     @RequestBody @Valid ACEconomyDTO economyDTO) {
        if (sessionToken == null || !userService.isAuthenticated(sessionToken)) {
            return ResponseEntity.status(401).build();
        }

        double currentMonthlyConsumption = acEconomyService.calcMonthlyConsumption(
                economyDTO.getNumberOfAirConditioners(),
                economyDTO.getBtu(),
                economyDTO.getTemperature()
        );

        double idealMonthlyConsumption = acEconomyService.calcIdealMonthlyConsumption(
                economyDTO.getNumberOfAirConditioners(),
                economyDTO.getBtu()
        );

        double monthlyEconomyPercentage = acEconomyService.calcMonthlyEconomyPercentage(
                currentMonthlyConsumption,
                idealMonthlyConsumption
        );

        return ResponseEntity.ok(monthlyEconomyPercentage);
    }
}
