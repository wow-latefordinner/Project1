package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
//import java.util.Set;

/**
 * This class is deprecated.
 */

public class SellerService {

    List<Seller> sellerList;

    public static Logger log = LoggerFactory.getLogger(SellerService.class);

    public SellerService (){
        sellerList = new ArrayList<>();
    }

    public void insertSeller(Seller seller) throws SellerException{

        if (seller.getSeller() == null || seller.getSeller().isEmpty()) {
            log.warn("Seller not provided");
            throw new SellerException("No seller provided");
        }

        if (!sellerList.add(seller)) {
            log.warn("Seller already exists");
            throw new SellerException("Seller already exists");
        }
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }
}
