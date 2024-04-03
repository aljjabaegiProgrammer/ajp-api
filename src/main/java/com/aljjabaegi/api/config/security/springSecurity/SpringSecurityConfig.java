package com.aljjabaegi.api.config.security.springSecurity;

import com.aljjabaegi.api.config.security.jwt.JwtAccessDeniedHandler;
import com.aljjabaegi.api.config.security.jwt.JwtAuthenticationEntryPoint;
import com.aljjabaegi.api.config.security.jwt.JwtFilter;
import com.aljjabaegi.api.config.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 인증처리와 401,403 에러 처리, 암호화 Security Filter
 *
 * @author GEONLEE
 * @since 2024-04-02
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    public static final String[] SWAGGER_URIS = {"swagger-ui.html", "/swagger-ui/**", "/api-docs/**"};
    public static final String[] IGNORE_URIS = {"/v1/login", "/v1/logout", "/v1/public-key", "/favicon.ico", "/error"};
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //기본 bcrypt 사용
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(IGNORE_URIS).permitAll()
                            .requestMatchers(SWAGGER_URIS).permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c ->
                        c.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .apply(new JwtSecurityConfig(tokenProvider, messageConfig)); /*spring 6.2 deprecated*/
        return http.build();
    }
}