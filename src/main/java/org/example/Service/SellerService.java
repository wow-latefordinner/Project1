package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashSet;
import java.util.Set;


public class SellerService {

    HashSet<Seller> sellerHashSet;

    public static Logger log = LoggerFactory.getLogger(SellerService.class);

    public SellerService (){
        sellerHashSet = new HashSet<>();
    }

    public void insertSeller(Seller seller) throws SellerException{

        if (seller.getSeller() == null || seller.getSeller().isEmpty()) {
            log.warn("Seller not provided");
            throw new SellerException("No seller provided");
        }

        if (!sellerHashSet.add(seller)) {
            log.warn("Seller already exists");
            throw new SellerException("Seller already exists");
        };
    }

    public HashSet<Seller> getSellerList() {
        return sellerHashSet;
    }
}
