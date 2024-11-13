package com.efficiencynow.efficiencynow.controllers;

import com.efficiencynow.efficiencynow.dtos.LampEconomyDTO;
import com.efficiencynow.efficiencynow.services.LampEconomyService;
import com.efficiencynow.efficiencynow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operações relacionadas à economia de lâmpadas.
 */
@RestController
@RequestMapping("/lamp-economy")
public class LampEconomyController {
    @Autowired
    private LampEconomyService lampEconomyService;

    @Autowired
    private UserService userService;

    /**
     * Calcula a economia total de energia com base nos tipos de lâmpadas fornecidos.
     *
     * @param sessionToken   Token de sessão do usuário, obtido a partir de um cookie.
     * @param lampEconomyDTO Objeto DTO contendo o número de lâmpadas fluorescentes, incandescentes e halógenas.
     * @return ResponseEntity com o valor da economia total ou status 401 se o usuário não estiver autenticado.
     */
    @PostMapping("/calc")
    public ResponseEntity<Double> calcEconomy(@CookieValue(name = "SESSION", required = false) String sessionToken,
                                              @RequestBody @Valid LampEconomyDTO lampEconomyDTO) {
        if (sessionToken == null || !userService.isAuthenticated(sessionToken)) {
            return ResponseEntity.status(401).build();
        }

        double totalEconomy = lampEconomyService.calculateTotalEconomy(
                lampEconomyDTO.getNumberOfFluorescentLamps(),
                lampEconomyDTO.getNumberOfIncandescentLamps(),
                lampEconomyDTO.getNumberOfHalogenLamps()
        );

        return ResponseEntity.ok(totalEconomy);
    }
}
