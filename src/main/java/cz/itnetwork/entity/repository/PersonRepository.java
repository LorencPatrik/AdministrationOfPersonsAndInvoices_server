package cz.itnetwork.entity.repository;

import cz.itnetwork.service.model.PersonStatisticAllTime;
import cz.itnetwork.service.model.PersonStatisticLastYear;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity> {

    List<PersonEntity> findAllByIdentificationNumber(String identificationNumber);

    List<PersonEntity> findAllByHidden(boolean hidden);

    /**
     * Returns a List of statistics of all persons for all time.
     *
     * @return List of individual person statistics
     */
    @Query(value = """
            SELECT NEW cz.itnetwork.service.model.PersonStatisticAllTime(p.id, p.name, SUM(i.price), COUNT(i.price))
            FROM person p LEFT JOIN p.sales i
            WHERE hidden = false
            GROUP BY p.id
            """)
    List<PersonStatisticAllTime> getStatisticsAllTime();

    /**
     * Returns a List of statistics of persons who had an income last year
     *
     * @return List of individual person statistics
     */
    @Query(value = """
            SELECT NEW cz.itnetwork.service.model.PersonStatisticLastYear(p.id, SUM(i.price), COUNT(i.price))
            FROM person p LEFT JOIN p.sales i
            WHERE hidden = false
            AND Year(i.dueDate) = Year(now) - 1
            GROUP BY p.id
            """)
    List<PersonStatisticLastYear> getStatisticsLastYear();
}