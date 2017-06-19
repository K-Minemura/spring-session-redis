package jp.gr.java_conf.kmine27.spring.session.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Value("${security.user.name}")
    private String username;

    @Value("${security.user.password}")
    private String password;

    /*
     * (非 Javadoc)
     * 
     * @see
     * org.springframework.security.config.annotation.authentication.configurers
     * .GlobalAuthenticationConfigurerAdapter#init(org.springframework.security.
     * config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(username).password(password).roles("ADMIN", "ACTUATOR")
                ;
    }

}
