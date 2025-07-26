package com.payement.service;

import com.payement.dto.ProductRequest;
import com.payement.dto.StripeResponse;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@Service
public class StripeService {
    @Value("${stripe.secretkey}")
    private String secretKey;
    @Value("${server.port}")
    private String port;
    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getProductName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getProductCurrency() == null ? "USD" : productRequest.getProductCurrency())
                .setUnitAmount(productRequest.getProductAmount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getProductQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:"+this.port+"/success")
                .setCancelUrl("http://localhost:"+this.port+"/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException ex) {
            //log error
            System.out.println(ex.getMessage());
        }

    return StripeResponse.builder()
            .status(session != null ? "success" : "failed")
            .message("Payment session created")
            .sessionId(session != null ? session.getId() : null)
            .sessionUrl(session != null ? session.getUrl() : null)
            .build();

    }
}
