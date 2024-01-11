package com.example.Clinic_API.specification;

import com.example.Clinic_API.enums.OperationEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class SpecificationFilter  {

    private SearchCriteria searchCriteria;

    public SpecificationFilter(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }


    public static Specification generate(SearchCriteria searchCriteria){
        switch (searchCriteria.getOperation()){
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(searchCriteria.getKey()),"%" + searchCriteria.getValue() + "%");
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(searchCriteria.getKey()),
                                castToRequiredType(root.get(searchCriteria.getKey()).getJavaType(),searchCriteria.getValue()));
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(searchCriteria.getKey()))
                                .value(castToRequiredType(root.get(searchCriteria.getKey()).getJavaType(),searchCriteria.getValues()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(searchCriteria.getKey()),
                                (Number) castToRequiredType(root.get(searchCriteria.getKey()).getJavaType(),searchCriteria.getValue()));
            default:
                throw new RuntimeException("This operation is invalid");
        }
    }

    public static Object castToRequiredType(Class fieldType, String value){
        if (fieldType.isAssignableFrom(Double.class))
            return Double.parseDouble(value);
        else if (fieldType.isAssignableFrom(Integer.class))
            return Integer.parseInt(value);
        else if (fieldType.isAssignableFrom(Enum.class))
            return Enum.valueOf(fieldType, value);
        return null;
    }

    public static Object castToRequiredType(Class fieldType, List<String> values){
        return values.stream().map(value -> castToRequiredType(fieldType,value)).collect(Collectors.toList());
    }

}
