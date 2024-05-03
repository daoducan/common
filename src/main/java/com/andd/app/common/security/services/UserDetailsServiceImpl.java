package com.andd.app.common.security.services;

import com.andd.app.common.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public UserDetails loadUserByJwtToken(String jwt) throws UsernameNotFoundException {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        String roles = jwtUtils.getRolesFromJwtToken(jwt);

        List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return UserDetailsImpl.build(username, "*", authorities);
    }
}
