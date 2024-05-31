package org.zerock.safefast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.safefast.service.login.CustomUserDetailsService;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/member/login", "/member/reset_password_form", "/member/forgot_id","/css/**","/js/**","/image/**"
//                                        ,"/member/create_account_form").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/member/login")
//                                .loginProcessingUrl("/member/login") // 이 URL로 로그인 폼이 POST 요청을 보냄
//                                .defaultSuccessUrl("/index", true)
//                                .failureUrl("/member/login?error=true")
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/member/logout")
//                                .logoutSuccessUrl("/member/login")
//                                .permitAll()
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement
//                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션 생성 정책 설정
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("/**")
                .authorizeHttpRequests(
                        (authorizeHttpRequests) ->
                                authorizeHttpRequests
                                        .requestMatchers("/member/**","/css/**","/js/**","/image/**","/contract/**","/item/**","/inventory/**",
                                                "/inventory_management/**","/invoicing/**","/po_status/**","/api/stats/**","/procurement/**",
                                                "/production/**","/item/**","/progress_check_item/**","/purchase_order/**","/receive/**", "/file/**").permitAll()
                                        .requestMatchers("/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                );
        http
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/member/login")
                                .loginProcessingUrl("/login") // 이 URL로 로그인 폼이 POST 요청을 보냄
                                .usernameParameter("empNumber")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/index", true)
                                .failureUrl("/member/login?error=true")
                                .permitAll()
                );
        http
                .logout(logout -> logout
                                .logoutUrl("/member/logout")
                                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                                .permitAll()
                );
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 세션을 비워주고 로그인 페이지로 리다이렉트
                    request.getSession().invalidate();
                    response.sendRedirect("/user/login");
                })
        );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select emp_number, password, true as enabled "
                        + "from member "
                        + "where emp_number = ? ")
                .authoritiesByUsernameQuery("select m.emp_number, r.authority "
                        + "from member m inner join authority r on m.emp_number = r.emp_number "
                        + "where m.emp_number = ?");
    }


}

