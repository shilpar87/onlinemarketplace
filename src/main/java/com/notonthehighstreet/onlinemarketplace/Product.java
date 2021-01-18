package com.notonthehighstreet.onlinemarketplace;

import java.util.Objects;

public final class Product {

    private final String code;
    private final String name;
    private final Double price;

    public Product(final String code, final String name, final Double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                code.equals(product.code) &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, price);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.notonthehighstreet.onlinemarketplace.Product{");
        sb.append("code=").append(code);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
