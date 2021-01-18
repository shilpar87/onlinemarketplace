package com.notonthehighstreet.onlinemarketplace;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CheckoutTest {
    private static final Product PR_001 = new Product("001", "Travel Card Holder", 9.25);
    private static final Product PR_002 = new Product("002", "Personalised cufflinks", 45.00);
    private static final Product PR_003 = new Product("003", "Kids T-shirt", 19.95);

    @Test
    public void shouldScanValidProductAndAddItToTheList() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);

        checkout.scan(PR_001);
        assertThat(checkout.getProducts()).hasSize(1).containsExactly(PR_001);

    }

    @Test
    public void shouldScanValidProductsAddThemToTheListInTheRightOrder() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        checkout.scan(PR_001);
        checkout.scan(PR_002);
        assertThat(checkout.getProducts()).hasSize(2).containsExactly(PR_001, PR_002);

    }

    @Test
    public void shouldThrowExceptionWhenScanNullProduct() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        assertThatThrownBy(() -> checkout.scan(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product to be added to scan can't be null");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithEmptyCode() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product("", "Travel Card Holder", 9.25);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product code can't be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithNullCode() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product(null, "Travel Card Holder", 9.25);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product code can't be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithEmptyName() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product("001", "", 9.25);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name can't be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithNullName() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product("001", null, 9.25);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name can't be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithNullPrice() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product("001", "test", null);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product price should be a positive number");
    }

    @Test
    public void shouldThrowExceptionWhenScanProductWithNegativePrice() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        Product product = new Product("001", "test", -12.0);
        assertThatThrownBy(() -> checkout.scan(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product price should be a positive number");
    }

    @Test
    public void shouldReturnZeroTotalWhenNoProductsAddedToCard() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        assertThat(checkout.total()).isEqualTo(0.0);
    }

    @Test
    public void shouldReturnActualTotalWhenPromotionsApplied() {
        Checkout checkout = new Checkout(new ArrayList<>());
        checkout.scan(PR_001);
        checkout.scan(PR_002);
        checkout.scan(PR_003);
        assertThat(checkout.total()).isEqualTo(74.2);
    }

    @Test
    public void shouldApply10PercentDiscountWhenTotalMoreThan60() {
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        checkout.scan(PR_001);
        checkout.scan(PR_002);
        checkout.scan(PR_003);
        assertThat(checkout.total()).isEqualTo(66.78);
    }

    @Test
    public void shouldApplyTravelCardDiscountWhen2TravelCardProductsAreScanned(){
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        checkout.scan(PR_001);
        checkout.scan(PR_003);
        checkout.scan(PR_001);
        assertThat(checkout.total()).isEqualTo(36.95);

    }

    @Test
    public void shouldApplyBothTravelCardDiscountAnd10PercentWhen2TravelCardProductsAreScannedAndTotalIsMoreThan60(){
        List<PromotionalRule> promotionalRules = Arrays.asList(PromotionalRule.travelCardDiscount(), PromotionalRule.tenPercent());
        Checkout checkout = new Checkout(promotionalRules);
        checkout.scan(PR_001);
        checkout.scan(PR_002);
        checkout.scan(PR_001);
        checkout.scan(PR_003);
        assertThat(checkout.total()).isEqualTo(73.76);

    }

}
