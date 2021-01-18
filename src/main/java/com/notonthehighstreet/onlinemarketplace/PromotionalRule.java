package com.notonthehighstreet.onlinemarketplace;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public interface PromotionalRule {


    Double calculateDiscountedPrice(final List<Product> products, final Double total);

    static PromotionalRule tenPercent() {
        return (products, total) -> {
            final double discount = 0.1;
            return total > 60.00 ? total - total * discount : total;
        };

    }

    static PromotionalRule travelCardDiscount() {
        final String travelProductID = "001";
        final double travelProductDiscountPrice = 8.5;

        return (products, total) -> {
            if (isNull(products)) {
                return total;
            }
            List<Product> travelProducts = products.stream().filter(product -> product.getCode().equals(travelProductID)).collect(Collectors.toList());
            if (travelProducts.size() > 1) {
                return total - (travelProducts.get(0).getPrice() - travelProductDiscountPrice) * travelProducts.size();
            }
            return total;
        };
    }


}
