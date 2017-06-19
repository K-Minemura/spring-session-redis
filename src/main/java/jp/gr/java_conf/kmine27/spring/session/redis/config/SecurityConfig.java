package jp.gr.java_conf.kmine27.spring.session.redis.config;

import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * <b>セキュリティコンフィグ</b><br/>
 * @author 054501
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * (非 Javadoc)
     * 
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#configure(org.springframework.security.
     * config.annotation.web.builders.WebSecurity)
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                            "/favicon.ico",
                            "/image/**",
                            "/css/**",
                            "/fonts/**",
                            "/js/**");
    }

    /*
     * (非 Javadoc)
     * 
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#configure(org.springframework.security.
     * config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可の設定
        http.authorizeRequests()
            .antMatchers("/")
            .permitAll()
            .anyRequest().authenticated()
            .and()
        // ログイン設定
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/auth")
            .failureUrl("/login?error")
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
            .and()

        // ログアウト設定
        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/")
            .permitAll()
            .and()

        // Httpレスポンスヘッダ
        .headers()
            .contentSecurityPolicy("frame-ancestors none").and()
            .and()

        // 認証失敗時の制御
        .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
            .and()

        // ログイン時のセッションの取り扱い
        .sessionManagement()
            .sessionFixation()
                .changeSessionId();
    }

    /**
     * 【機能名】<b>認証失敗ハンドラ</b><br/>
     * 【概要】  認証失敗ハンドラを設定します。<br/>
     * 【作成日、作成者】<br/>
     *   2017/06/05 054501<br/>
     * 【更新日、更新者、更新概要】<br/>
     *   2017/06/05 054501<br/>
     * @return AccessDeniedHandler
     */
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl invalidCsrfTokenHandler = new AccessDeniedHandlerImpl();
        invalidCsrfTokenHandler.setErrorPage("/invalidCsrf.html"); // TODO
        AccessDeniedHandlerImpl missingCsrfTokenHandler = new AccessDeniedHandlerImpl();
        missingCsrfTokenHandler.setErrorPage("/missingCsrf.html"); // TODO

        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers =
                new LinkedHashMap<>();
        handlers.put(InvalidCsrfTokenException.class, invalidCsrfTokenHandler);
        handlers.put(MissingCsrfTokenException.class, missingCsrfTokenHandler);
        
        AccessDeniedHandlerImpl defaultHandler = new AccessDeniedHandlerImpl();
        defaultHandler.setErrorPage("/accessDeniedError.html"); // TODO

        return new DelegatingAccessDeniedHandler(handlers, defaultHandler);
    }

    /**
     * 【機能名】<b>パスワードエンコーダの取得</b><br/>
     * 【概要】  パスワードエンコーダを取得します。<br/>
     * 【作成日、作成者】<br/>
     *   2017/06/05 054501<br/>
     * 【更新日、更新者、更新概要】<br/>
     *   2017/06/05 054501<br/>
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
