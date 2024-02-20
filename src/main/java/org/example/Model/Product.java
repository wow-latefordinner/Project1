package org.example.Model;

import java.util.Objects;

public class Product {

    public long id;
    public String name;
    public double price;

    public int sellerId;

    public Product() {

    }

    public Product(long id, String name, double price, int sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && sellerId == product.sellerId && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, sellerId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sellerId=" + sellerId +
                '}';
    }
}
