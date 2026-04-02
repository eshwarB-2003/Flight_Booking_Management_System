package com.flightbooking.AdminService.Security;



import com.flightbooking.AdminService.Entity.User;
import com.flightbooking.AdminService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean active = "ACTIVE".equalsIgnoreCase(user.getAccountStatus());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                active,
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}