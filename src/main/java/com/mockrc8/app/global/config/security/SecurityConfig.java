package com.mockrc8.app.global.config.security;

import com.mockrc8.app.global.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtService jwtService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api이므로 기본설정 미사용
                .csrf().disable() // rest api이므로 csrf 보안 미사용
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
                .and()
                .authorizeRequests()
                .antMatchers("/employment/**").permitAll()
                .antMatchers("/company/**").permitAll()
                .antMatchers("/oauth").permitAll()
                .antMatchers("/sign-up").permitAll()
                .antMatchers("/email-validation").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/login/kakao").permitAll()
                .antMatchers("/insights/**").permitAll()
                .antMatchers("/user/**").permitAll() // 테스트하기위해 추가했습니다.
                .antMatchers("/events/**").permitAll()
                .antMatchers("/community/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가
    }
}