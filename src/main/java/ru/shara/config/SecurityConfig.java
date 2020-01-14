package ru.shara.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();

        http.httpBasic().authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(
                "$2y$12$aBhpqOJE8LfAwd7SCmJPR.xo9lfxR/A78H.hDIkHybgmReaRQzuCq").authorities("USER");
        auth.inMemoryAuthentication().withUser("root").password(
                "$2y$12$yMLF3lvEO2q41wrPs2VUd.A.apNTv2./UFdoLNylFCHQbgWj06bsG").authorities("USER", "ADMIN");
    }

//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    public void configurationGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        String password = "123";
//
//        String encryptedPass = this.passwordEncoder().encode(password);
//        System.out.println("Encoded password of 123 = " + encryptedPass);
//
//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> mngConfig = auth.inMemoryAuthentication();
//
//        //Defines 2 users, stored in memory
//        UserDetails admin = User.withUsername("Zaq").password(encryptedPass).roles("ADMIN").build();
//        UserDetails user = User.withUsername("Sad").password(encryptedPass).roles("User").build();
//
//        mngConfig.withUser(admin);
//        mngConfig.withUser(user);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//               .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JWTLoginFilter("/login",authenticationManager()),
//                    UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();
//
//    }
}
