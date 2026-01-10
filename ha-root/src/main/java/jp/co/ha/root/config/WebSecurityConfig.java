package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
@EnableMethodSecurity
public class WebSecurityConfig {

    /** 認証不要URL */
    private static final String[] AUTH_IGNORE_URL = new String[] { "/api/root/login",
            "/api/root/user", "/api/root/healthcheck" };

    /** 認証サービス */
    @Autowired
    @Qualifier("authService")
    private UserDetailsService userDetailsService;
    /** パスワードEncoder */
    @Autowired
    @Qualifier("passwordEncoderImpl")
    private PasswordEncoder passwordEncoder;
    /** アプリケーション設定ファイル */
    @Autowired
    private ApplicationProperties applicationProperties;
    /** Provider */
    @Autowired
    private JwtSigningKeyProvider provider;

    /**
     * securityFilterChain
     * 
     * @param http
     *     HttpSecurity
     * @return securityFilterChain
     * @throws Exception
     *     セキュリティ設定処理失敗
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager manager = builder.build();

        http
                .authenticationManager(manager)
                // CORS 設定
                .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
                // 認証不要URL 許可
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_IGNORE_URL).permitAll()
                        .anyRequest().authenticated())
                // CSRF 無効化
                .csrf(csrf -> csrf.disable())
                // 認証フィルター 追加
                .addFilter(
                        new JWTAuthenticationFilter(manager, passwordEncoder, provider))
                // 認可フィルター 追加
                .addFilter(new JWTAuthorizationFilter(manager, provider))
                // セッション 設定
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    /**
     * configure
     * 
     * @param auth
     *     AuthenticationManagerBuilder
     * @throws Exception
     *     認証処理失敗
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

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
