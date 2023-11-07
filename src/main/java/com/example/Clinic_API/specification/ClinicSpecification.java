package com.example.Clinic_API.specification;

import com.example.Clinic_API.entities.Clinic;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ClinicSpecification implements Specification<Clinic> {

    // cấu hình 1 specification

    private SearchCriteria searchCriteria;

    public ClinicSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Clinic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperation().equalsIgnoreCase(":"))
            if (root.get(searchCriteria.getKey()).getJavaType()==String.class)
                return criteriaBuilder.like(root.get(searchCriteria.getKey()) ,"%" + searchCriteria.getValue()+"%");
            else
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());

        return null;
    }
}
