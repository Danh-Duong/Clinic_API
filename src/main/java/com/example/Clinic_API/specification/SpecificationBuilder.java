package com.example.Clinic_API.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Clinic_API.enums.OperationEnum;

public class SpecificationBuilder {

    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        params=new ArrayList<>();
    }

    public void with(String key, OperationEnum operation , String value){
        params.add(new SearchCriteria(key,operation,value));
    }

    public Specification<?> build(){
        if (params.size()==0)
            return null;
        List<Specification> specs=params.stream()
                .map(SpecificationFilter::generate)
                .collect(Collectors.toList());
        Specification result=specs.get(0);
        for (int i=1;i<params.size();i++)
            result= Specification.where(result).and(specs.get(i));
        return result;
    }
}
