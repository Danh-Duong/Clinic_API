//package com.example.Clinic_API;
//
//import com.example.Clinic_API.entities.District;
//import com.example.Clinic_API.entities.Province;
//import com.example.Clinic_API.payload.DistrictResponse;
//import com.example.Clinic_API.payload.ProvinceResponse;
//import com.example.Clinic_API.repository.DistrictRepository;
//import com.example.Clinic_API.repository.ProvinceRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class InitValueAddress {
//    private final String url="https://provinces.open-api.vn/api/?depth=2";
//    @Autowired
//    ProvinceRepository provinceRepository;
//
//    @Autowired
//    DistrictRepository districtRepository;
//
//    @Autowired
//    WebClient webClient;
//    @Autowired
//    ModelMapper modelMapper;
//
//    @PostConstruct
//    public void innitData(){
//        try{
//            if (provinceRepository.findAll().size()==0){
//                List<ProvinceResponse> response =webClient.get()
//                        .uri("https://provinces.open-api.vn/api/?depth=2")
//                        .retrieve()
//                        .bodyToFlux(ProvinceResponse.class)
//                        .collectList()
//                        .block();
//                for (ProvinceResponse p: response)  {
//                    Province province=modelMapper.map(p,Province.class);
//                    List<District> districts=new ArrayList<>();
//                    for (DistrictResponse d: p.getDistricts()){
//                        District district=modelMapper.map(d,District.class);
//                        districts.add(district);
//                        System.out.println("Danh: " +  district.toString());
//                    }
//                    province.setDistricts(districts);
//                    provinceRepository.save(province);
//                }
//
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
