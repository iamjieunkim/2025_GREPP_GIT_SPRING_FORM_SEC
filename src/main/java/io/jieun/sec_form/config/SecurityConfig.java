package io.jieun.sec_form.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//public class SecurityConfig extends WebSecurityConfiguration { 이건 옛날 방식이니까 사용하지 마삼 빈 사용해
@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //여기에 설정을 하나씩 넣어줘야됨
        return http
                .csrf(csrf -> csrf.disable() )
//                .formLogin(
//                        form -> {
//                            form.failureForwardUrl("/login?error=true")
//                                    //.successForwardUrl("/index") //로그인에 성공하면 페이지는 그대로 둠
//                                    //.defaultSuccessUrl("/happy")
//                                    .usernameParameter("loginId")
//                                    .passwordParameter("loginPwd")
//                                    .loginProcessingUrl("/signin")
//                                    .loginPage("/signin")
//                                    .permitAll();
//                        }
//                )
                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
                .logout( logout -> {
                    logout.logoutUrl("/signout")
                            .logoutSuccessUrl("/")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true) //우리 서버에서 갖고있는 httpsession을 무효화 시키겠다
                            .deleteCookies("JSESSIONID");
                })
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/signup", "/signin")
                                    .anonymous()
                                    .requestMatchers("/user/**")
                                    .hasRole("MEMBER")//hasRole로 권한검사를 할 수 있다, 이걸 가지고 있어야 인가를 가질 수 있음
                                    .anyRequest()
                                    //.denyAll(); //아예 접근 못하게 막고 싶을때 사용
                                     .authenticated();
                        }
                )
                .build();
    }

    //인가
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        String targetPwd = "1234";
        String encoded = passwordEncoder().encode(targetPwd);

        log.info("encoded = {}", encoded);

        //얘는 InMemoryUserDetailsManager 객체를 user을만들어줌
        manager.createUser(
                User.withUsername("user")
                        .password(encoded)
                        //.roles()
                        .build()
        );
        return manager;
    }

}
