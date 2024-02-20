package org.example.Model;

import java.util.Objects;

public class Seller {

    public long id;

    public String seller;


    public Seller(){

    }

    public Seller(long id, String seller) {
        this.id = id;
        this.seller = seller;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == seller1.id && Objects.equals(seller, seller1.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seller);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", seller='" + seller + '\'' +
                '}';
    }
}
