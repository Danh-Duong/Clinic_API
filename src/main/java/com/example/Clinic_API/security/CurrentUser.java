package com.example.Clinic_API.security;

import com.example.Clinic_API.entities.Role;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {

    @Autowired
    UserRepository userRepository;

    private User user;
    private Boolean isAdmin;

    public CurrentUser(User user, Boolean isAdmin) {
        this.user = user;
        this.isAdmin = isAdmin;
    }

    public CurrentUser getInfoUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User currentUser= userRepository.findByUsername(username);
        // bắt buộc phải setUser nha
        setUser(currentUser);
        String roles = "";
        for (Role role: currentUser.getRoles())
            roles+=" " + role.getCode();
        // bắt buộc phải setIsAdmin nha
        setIsAdmin(roles.contains("ROLE_ADMIN"));
        return new CurrentUser(user, isAdmin);
    }
}
