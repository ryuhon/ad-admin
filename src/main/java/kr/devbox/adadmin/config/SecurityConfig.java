package kr.devbox.adadmin.config;

import kr.devbox.adadmin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private AuthService authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
        final CustomBasicAuthenticationFilter customBasicAuthFilter = new CustomBasicAuthenticationFilter(this.authenticationManager() );



        http.csrf().disable().addFilterBefore(tokenFilter, BasicAuthenticationFilter.class).addFilter(customBasicAuthFilter)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/v2/api-docs").permitAll()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/**").access("hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .and()
                .sessionManagement().maximumSessions(1);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService)
                .passwordEncoder(authService.passwordEncoder())
                ;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}