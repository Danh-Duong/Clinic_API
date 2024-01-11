package com.example.Clinic_API;

import com.example.Clinic_API.entities.Role;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.RoleRepository;
import com.example.Clinic_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Component
public class InitData {

    @Autowired
    UserRepository userReposiitory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    private static Role roleDoctor=null;
    private static Role roleUser=null;
    private static Role roleAdmin=null;

    @PostConstruct
    public void initData(){
        if (roleRepository.findByCode("ROLE_ADMIN")==null){
            roleAdmin=new Role();
            roleAdmin.setCode("ROLE_ADMIN");
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        if (roleRepository.findByCode("ROLE_USER")==null){
            roleUser=new Role();
            roleUser.setCode("ROLE_USER");
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }

        if (roleRepository.findByCode("ROLE_DOCTOR")==null){
            roleDoctor=new Role();
            roleDoctor.setCode("ROLE_DOCTOR");
            roleDoctor.setName("ROLE_DOCTOR");
            roleRepository.save(roleDoctor);
        }

        // thêm 3 tài khoản admin, user, doctor
        if (userReposiitory.findByUsername("admin")==null){
            User user=new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Collections.singletonList(roleAdmin));
            userReposiitory.save(user);
        }

        if (userReposiitory.findByUsername("doctor")==null){
            User user=new User();
            user.setUsername("doctor");
            user.setPassword(passwordEncoder.encode("doctor"));
            user.setRoles(Arrays.asList(roleDoctor,roleUser));
            userReposiitory.save(user);
        }

        if (userReposiitory.findByUsername("user")==null){
            User user=new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(Collections.singletonList(roleUser));
            userReposiitory.save(user);
        }
    }
}
