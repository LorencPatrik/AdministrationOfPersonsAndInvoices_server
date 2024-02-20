package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

    Page<InvoiceEntity> findAll(Pageable page);

    List<InvoiceEntity> findAllByBuyerIn(List<PersonEntity> personEntity);

    List<InvoiceEntity> findAllBySellerIn(List<PersonEntity> personEntity);

    /**
     * Returns statistic of all invoices for the last year and all time.
     *
     * @return One summary statistic
     */
    @Query(value = """
            SELECT NEW cz.itnetwork.dto.InvoiceStatisticDTO(SUM(i2.price), COUNT(i2.price), SUM(i.price), COUNT(i.price))
            FROM invoice i LEFT JOIN invoice i2 ON i.id = i2.id AND Year(i2.dueDate) = Year(now) - 1
            """)
    InvoiceStatisticDTO lastYearStatistic();
}