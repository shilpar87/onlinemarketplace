package com.notonthehighstreet.onlinemarketplace;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionRuleTest {

    @Test
    public void shouldApplyTenPercentDiscountWhenTotalIdMoreThan60() {
        PromotionalRule promotionalRule = PromotionalRule.tenPercent();

        Double expectedResult = 90.0;

        Double result = promotionalRule.calculateDiscountedPrice(new ArrayList<>(), 100.0);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void shouldNotApplyTenPercentDiscountWhenTotalIslessThan60() {
        PromotionalRule promotionalRule = PromotionalRule.tenPercent();

        Double expectedResult = 50.0;

        Double result = promotionalRule.calculateDiscountedPrice(new ArrayList<>(), 50.0);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void shouldNotApplyTenPercentDiscountWhenTotalIs60() {
        PromotionalRule promotionalRule = PromotionalRule.tenPercent();

        Double expectedResult = 60.0;

        Double result = promotionalRule.calculateDiscountedPrice(new ArrayList<>(), 60.0);

        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    public void shouldReducePriceOfTravelCardWhen2ArePresent() {
        Product pr1 = new Product("001", "Travel Card Holder", 9.25);
        List<Product> products = Arrays.asList(pr1, pr1);
        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 8.5 * 2;
        Double result = promotionalRule.calculateDiscountedPrice(products, 9.25 * 2);
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void shouldReducePriceOfTravelCardMoreThan2ArePresent() {
        Product pr1 = new Product("001", "Travel Card Holder", 9.25);
        List<Product> products = Arrays.asList(pr1, pr1, pr1);
        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 8.5 * 3;
        Double result = promotionalRule.calculateDiscountedPrice(products, 9.25 * 3);
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void shouldReducePriceOfTravelCard2ArePresentWithOtherProducts() {
        Product pr1 = new Product("001", "Travel Card Holder", 9.25);
        Product pr2 = new Product("002", "Test pr", 10.0);
        List<Product> products = Arrays.asList(pr1, pr1, pr2, pr1, pr2);
        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 8.5 * 3 + 10 * 2;
        Double result = promotionalRule.calculateDiscountedPrice(products, 9.25 * 3 + 10 * 2);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void shouldNotReducePriceOfTravelCardWhen1IsPresent() {
        Product pr1 = new Product("001", "Travel Card Holder", 9.25);
        List<Product> products = Arrays.asList(pr1);
        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 9.25;
        Double result = promotionalRule.calculateDiscountedPrice(products, 9.25 );
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void shouldNotReducePriceOfTravelCardWhenProductsIsEmpty() {

        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 0.0;
        Double result = promotionalRule.calculateDiscountedPrice(new ArrayList<>(), 0.0 );
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void shouldNotReducePriceOfTravelCardWhenProductsIsNull() {

        PromotionalRule promotionalRule = PromotionalRule.travelCardDiscount();

        Double expectedResult = 0.0;
        Double result = promotionalRule.calculateDiscountedPrice(null, 0.0 );
        assertThat(result).isEqualTo(expectedResult);

    }
}
