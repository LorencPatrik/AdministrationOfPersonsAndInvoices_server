package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long invoiceNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersonEntity seller;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersonEntity buyer;

    @Column(nullable = false)
    private LocalDate issued;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int vat;

    private String note;
}
