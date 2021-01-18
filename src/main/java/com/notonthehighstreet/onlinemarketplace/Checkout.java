package com.notonthehighstreet.onlinemarketplace;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public final class Checkout {

    private final List<Product> products;

    private final List<PromotionalRule> promotionalRules;

    public Checkout(final List<PromotionalRule> promotionalRules) {
        this.promotionalRules = promotionalRules;
        this.products = new ArrayList<>();
    }

    public void scan(final Product product) {
        validateProduct(product);
        products.add(product);
    }

    public double total() {
        double total = getProducts().stream().map(product -> product.getPrice()).reduce(0.0, Double::sum);

        for (PromotionalRule promotionalRule : promotionalRules) {
            total = promotionalRule.calculateDiscountedPrice(getProducts(), total);
        }
        return roundUpValue(total, 2);
    }

    private Double roundUpValue(final Double total, final int places) {
        BigDecimal bd = new BigDecimal(Double.toString(total));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void validateProduct(final Product product) {
        if (isNull(product)) {
            throw new IllegalArgumentException("Product to be added to scan can't be null");
        }

        String productCode = product.getCode();
        if (isNull(productCode) || productCode.isEmpty()) {
            throw new IllegalArgumentException("Product code can't be null or empty");
        }
        String productName = product.getName();
        if (isNull(productName) || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty");
        }
        Double productPrice = product.getPrice();

        if (isNull(productPrice) || productPrice <= 0) {
            throw new IllegalArgumentException("Product price should be a positive number");
        }


    }

    public List<Product> getProducts() {
        return products;
    }


}