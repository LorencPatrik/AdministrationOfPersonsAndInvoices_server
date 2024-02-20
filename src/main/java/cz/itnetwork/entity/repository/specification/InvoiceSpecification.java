package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {
    final InvoiceFilter invoiceFilter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (invoiceFilter.getBuyerID() != null) {
            Join<PersonEntity, InvoiceEntity> directorJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(directorJoin.get(PersonEntity_.ID), invoiceFilter.getBuyerID()));
        }
        if (invoiceFilter.getSellerID() != null) {
            Join<PersonEntity, InvoiceEntity> directorJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(directorJoin.get(PersonEntity_.ID), invoiceFilter.getSellerID()));
        }
        if (invoiceFilter.getProduct() != null) {
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT), addChars(invoiceFilter.getProduct())));
        }
        if (invoiceFilter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMinPrice()));
        }
        if (invoiceFilter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMaxPrice()));
        }
        if (invoiceFilter.getInvoiceNumber() != null) {
            String filterItem = addChars(invoiceFilter.getInvoiceNumber());
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.INVOICE_NUMBER).as(String.class), filterItem));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    // region: Private methods

    private String addChars(String filterItem) {
        return String.format("%%%s%%", filterItem);
    }

    // endregion
}

