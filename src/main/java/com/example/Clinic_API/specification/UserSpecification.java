package com.example.Clinic_API.specification;

import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private static final String ADDRESS="address";
    private static final String NAME="name";

    private static final String PROVINCE= "province";

    public Specification<User> likeName(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(NAME), name));
    }

////    thông tin được lấy dựa trên vị trí của bệnh viện
//    public Specification<User> equalProvinceId(Long provinceId){
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("roles"));
//    }
//
//    public Specification<User> equalDistrictId(Long districtId){
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get)
//    }
}
