package com.example.Clinic_API;

import com.example.Clinic_API.entities.Role;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.RoleRepository;
import com.example.Clinic_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class InitData {

    @Autowired
    UserRepository userReposiitory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void initData(){
        if (roleRepository.findByCode("ROLE_ADMIN")==null){
            Role role=new Role();
            role.setCode("ROLE_ADMIN");
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }

        if (roleRepository.findByCode("ROLE_USER")==null){
            Role role=new Role();
            role.setCode("ROLE_USER");
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }

        if (roleRepository.findByCode("ROLE_DOCTOR")==null){
            Role role=new Role();
            role.setCode("ROLE_DOCTOR");
            role.setName("ROLE_DOCTOR");
            roleRepository.save(role);
        }
        if (userReposiitory.findByUsername("danh")==null){
            User user=new User();
            user.setUsername("danh");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Collections.singletonList(roleRepository.findByCode("ROLE_ADMIN")));
            userReposiitory.save(user);
        }
    }

}
