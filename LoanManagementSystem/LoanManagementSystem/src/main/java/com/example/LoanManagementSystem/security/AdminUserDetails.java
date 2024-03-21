package com.example.LoanManagementSystem.security;

import com.example.LoanManagementSystem.dao.AdminRepository;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AdminUserDetails implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    AdminRepository adminRepo;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (userRepo.existsByName(name)){
            Optional<User> users = userRepo.findByName(name);
            return users.map(UserInfo::new).orElseThrow(() ->  new UsernameNotFoundException("user not found " + name));
        }

        Optional<Admin> admin = adminRepo.findByName(name);
        return admin.map(AdminInfo::new).orElseThrow(() ->  new UsernameNotFoundException("admin not found " + name));
    }
}
