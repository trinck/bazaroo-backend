package com.payement.controller;

import com.payement.dto.ProductRequest;
import com.payement.dto.StripeResponse;
import com.payement.service.StripeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProducktCheckoutController {
    private StripeService stripeService;
    public ProducktCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping(value = "/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
