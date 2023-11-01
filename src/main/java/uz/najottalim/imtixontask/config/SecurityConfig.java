package uz.najottalim.imtixontask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import uz.najottalim.imtixontask.security.JwtConfigure;
import uz.najottalim.imtixontask.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/employees/**").hasAnyAuthority("CEO", "HEAD_DEPARTMENT", "EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigure(jwtTokenProvider));
    }

}