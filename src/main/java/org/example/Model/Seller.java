package org.example.Model;

import java.util.Objects;

public class Seller {

    public String seller;

    public Seller(){

    }

    public Seller(String seller) {
        this.seller = seller;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller1 = (Seller) o;
        return Objects.equals(seller, seller1.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "seller='" + seller + '\'' +
                '}';
    }
}
