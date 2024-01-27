package com.rpp95.bookstorerest;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.rpp95.bookstorerest.services.MyUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    jsr250Enabled = true,
    securedEnabled = true
)
public class SecurityConfig {

    @Value("${jwt.private.key}")
    private RSAPrivateKey rsaPrivateKey;
    @Value("${jwt.public.key}")
    private RSAPublicKey rsaPublicKey; 

    //To generate JWT Token
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey
                        .Builder(this.rsaPublicKey)
                        .privateKey(rsaPrivateKey)
                        .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    //To decode JWT Token
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    //Extract Authorities from Roles Claim
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
 
    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
        throws Exception{

        //Exception Handling for request without any bearer token
        http = http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                     .accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        }); 

        http.csrf()
            .disable() //till this csrf security in not disabled we call perform modification in the data
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/register","/login").permitAll()
            .anyRequest().authenticated()
            .and()
            // .httpBasic()
            // .and()
            // .authenticationProvider(authenticationProvider())
            .oauth2ResourceServer()
            .jwt();
        return http.build();
    }

    @Autowired
    private MyUserDetailsService detailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService users(){
        UserDetails user1 = User.builder()
                                .username("harry")
                                .password(this.passwordEncoder().encode("abc123"))
                                .roles("HR","ADMIN")
                                .build();

        UserDetails user2 = User.builder()
                                .username("mike")
                                .password(this.passwordEncoder().encode("abc123"))
                                .roles("IT","ADMIN")
                                .build();   
                                
        UserDetails user3 = User.builder()
                                .username("scott")
                                .password(this.passwordEncoder().encode("abc123"))
                                .roles("HR")
                                .build();

        UserDetails user4 = User.builder()
                                .username("king")
                                .password(this.passwordEncoder().encode("abc123"))
                                .roles("IT")
                                .build();
                               
        //InMemoryUserDetailsManager provides support for username/password
        //based authentication that is stored in memory
        return new InMemoryUserDetailsManager(user1, user2, user3, user4);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
