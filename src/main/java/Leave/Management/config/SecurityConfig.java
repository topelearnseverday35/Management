package Leave.Management.config;

import Leave.Management.entity.User;
import Leave.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;


import java.util.Optional;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final UserRepository userRepository;





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }





    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Fetch user from database
            Optional<User >userEntity = userRepository.findByEmail(username);
            if (userEntity.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }

            // Create UserDetails object from UserEntity
            User user = userEntity.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .build();

        };

    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().maximumSessions(2);
//    }


    @Bean
    public SecurityFilterChain defaultsecurityFilterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        http.authorizeRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/api/v1/Leave-Management/create-Account").permitAll();
            authorizeRequests.requestMatchers("/api/v1/Leave-Management/LogIn").permitAll();
            authorizeRequests.requestMatchers("/api/v1/Leave-Management/LogOut").permitAll();
            authorizeRequests.requestMatchers("/api/v1/Leave-Management/verify-Email").permitAll();
            authorizeRequests.anyRequest().authenticated();});
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(form -> form
                .loginPage("/api/v1/Leave-Management/LogIn"));
        http.csrf(AbstractHttpConfigurer::disable);
return http.build();
    }



    }


