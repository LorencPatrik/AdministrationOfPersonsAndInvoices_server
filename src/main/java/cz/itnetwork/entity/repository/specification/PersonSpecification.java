package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.PersonFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonSpecification implements Specification<PersonEntity> {
    final PersonFilter personFilter;

    @Override
    public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get(PersonEntity_.HIDDEN), personFilter.isHidden()));
        if (personFilter.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.NAME), addChars(personFilter.getName())));
        }
        if (personFilter.getIdentificationNumber() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.IDENTIFICATION_NUMBER), addChars(personFilter.getIdentificationNumber())));
        }
        if (personFilter.getTelephone() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.TELEPHONE), addChars(personFilter.getTelephone())));
        }
        if (personFilter.getMail() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.MAIL), addChars(personFilter.getMail())));
        }
        if (personFilter.getCity() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.CITY), addChars(personFilter.getCity())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    // region: Private methods

    private String addChars(String filterItem) {
        return String.format("%%%s%%", filterItem);
    }

    // endregion
}
