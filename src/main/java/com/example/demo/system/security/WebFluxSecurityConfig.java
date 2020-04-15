package com.example.demo.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebFluxSecurityConfig {
    @Resource
    private SecurityContextRepository securityContextRepository;

    public static final List<String> urlSkipList = new ArrayList<>();

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        WebFluxSecurityConfig.urlSkipList.add("/swagger-ui.html");
        WebFluxSecurityConfig.urlSkipList.add("/swagger-resources/**");
        WebFluxSecurityConfig.urlSkipList.add("/images/**");
        WebFluxSecurityConfig.urlSkipList.add("/webjars/**");
        WebFluxSecurityConfig.urlSkipList.add("/v2/api-docs");
        WebFluxSecurityConfig.urlSkipList.add("/configuration/ui");
        WebFluxSecurityConfig.urlSkipList.add("/configuration/security");

        return serverHttpSecurity
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .securityContextRepository(securityContextRepository)
                .exceptionHandling()
                .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.OK))
                .authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/api/security/error"))
                .and()
                .authorizeExchange()
                .pathMatchers(urlSkipList.toArray(String[]::new)).permitAll()
                .anyExchange().authenticated()
                .and().build();
//        return serverHttpSecurity.authorizeExchange().pathMatchers("/**").permitAll().and().build();//跳过检证
    }

}
