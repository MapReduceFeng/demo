package com.example.demo.system.security;


import com.example.demo.system.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextRepository.class);

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        return Mono.justOrEmpty(request.getHeaders().getFirst("token"))
                .filter(s -> JWTUtil.verifyToken(s))
                .flatMap(t -> {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(t, t,
                                    JWTUtil.verifyTokenTo(t).getArrayClaim().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
                            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(usernamePasswordAuthenticationToken);
                            return (Mono.just(usernamePasswordAuthenticationToken));
                        }
                ).map(SecurityContextImpl::new);
    }

}

