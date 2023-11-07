package com.example.Clinic_API.security;

import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userReposiitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userReposiitory.findByUsername(username);
        if (user==null)
            throw new UsernameNotFoundException("Username is none-exist");
        return new CustomUserDetails(user);
    }

}
