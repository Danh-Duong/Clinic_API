package com.example.Clinic_API.specification;

import com.example.Clinic_API.entities.Clinic;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class ClinicSpecification {

    private final static String VIETNAME="vietname";
    private final static String ADDRESS="address";


    private static Specification<Clinic> nameEqual(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(VIETNAME), name));
    }

    private static Specification<Clinic> equalWithProvinceId(Long provinceId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("district").get("province").get("id"), provinceId);
    }

    private static Specification<Clinic> equalWithDistrictId(Long districtId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("district").get("id"), districtId);
    }
}
