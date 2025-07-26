package com.payement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long productQuantity;
    private long productAmount;
    private String productName;
    private String productCurrency;

    //private String productId;
    //private String productDescription;
    //private String productCategory;

}
