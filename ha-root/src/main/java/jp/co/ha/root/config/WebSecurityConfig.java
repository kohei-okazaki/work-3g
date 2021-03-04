package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SpringSecurityによる認証設定クラス<br>
 * RootAPIの実行を行ってもよいかなどをトークンより判定する
 *
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 認証不要URL */
    private static final String[] AUTH_IGNORE_URL = new String[] { "/api/root/login",
            "/api/root/user" };

    /** 認証サービス */
    @Autowired
    @Qualifier("authService")
    private UserDetailsService userDetailsService;
    /** パスワードEncoder */
    @Autowired
    @Qualifier("passwordEncoderImpl")
    private PasswordEncoder passwordEncoder;
    /** アプリケーション設定ファイル情報 */
    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(this.corsConfigurationSource())
                .and().authorizeRequests()
                .antMatchers(AUTH_IGNORE_URL).permitAll()
                .anyRequest().authenticated()
                .and().logout()
                .and().csrf().disable()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),
                        passwordEncoder))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * {@linkplain CorsConfigurationSource}を返す
     *
     * @return CorsConfigurationSource
     */
    private CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addExposedHeader(JWTAuthenticationFilter.HEADER_AUTHORIZATION);
        corsConfiguration.addAllowedOrigin(applicationProperties.getFrontUrl());
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsSource;
    }

}
