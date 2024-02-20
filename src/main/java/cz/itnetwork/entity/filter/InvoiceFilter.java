package cz.itnetwork.entity.filter;

import cz.itnetwork.constant.InvoiceSortAttributes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceFilter {
    private Long buyerID;
    private Long sellerID;
    private String product;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String invoiceNumber;
    private Integer limit = 10;
    private Integer pageNumber = 0;
    private InvoiceSortAttributes nameOfAttribute = InvoiceSortAttributes.product;
    private boolean ascending = true;
}
