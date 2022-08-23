package com.mockrc8.app.global.oAuth.service;

import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.oAuth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(userEmail);
        if (user == null){
            throw new UsernameNotFoundException(userEmail);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        return AuthUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }

}
