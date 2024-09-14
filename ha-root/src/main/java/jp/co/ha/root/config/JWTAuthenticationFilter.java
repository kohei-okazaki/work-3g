package jp.co.ha.root.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.root.contents.auth.AuthInfo;
import jp.co.ha.root.contents.auth.request.LoginApiRequest;

/**
 * 認証フィルター
 *
 * @version 1.0.0
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /** 秘密鍵 */
    static final String SECRET = "rootapi";
    /** HTTPヘッダ:Authorization */
    static final String HEADER_AUTHORIZATION = "Authorization";
    /** トークンヘッダ接頭辞 */
    static final String TOKEN_PREFIX = "Bearer ";
    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(JWTAuthenticationFilter.class);
    /** ログインAPIのパス */
    private static final String LOGIN_API_PATH = "/api/root/login";
    /** ログインAPIのHTTPメソッド */
    private static final String LOGIN_API_METHOD = HttpMethod.POST.name();
    /** 要求パラメータ:ログインID */
    private static final String REQ_PARAM_SEQ_LOGIN_ID = "seq_login_id";
    /** 要求パラメータ:パスワード */
    private static final String REQ_PARAM_PASSWORD = "password";

    /** {@linkplain AuthenticationManager} */
    private AuthenticationManager authenticationManager;
    /** {@linkplain PasswordEncoderImpl} */
    @SuppressWarnings("unused")
    private PasswordEncoder passwordEncoder;

    /**
     * コンストラクタ
     *
     * @param authenticationManager
     *     認証情報管理クラス
     * @param passwordEncoder
     *     パスワードEncoder
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

        // ログインAPIのpathを変更
        setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(LOGIN_API_PATH, LOGIN_API_METHOD));

        // ログイン用のID/PWのパラメータ名を変更する
        setUsernameParameter(REQ_PARAM_SEQ_LOGIN_ID);
        setPasswordParameter(REQ_PARAM_PASSWORD);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        try {
            // requestパラメータからAPIリクエストFormを読み取る
            LoginApiRequest apiRequest = new ObjectMapper().readValue(
                    request.getInputStream(), LoginApiRequest.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    apiRequest.getSeqLoginId(), apiRequest.getPassword(),
                    new ArrayList<>());

            return authenticationManager.authenticate(token);

        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain, Authentication auth) throws IOException, ServletException {

        // 認証情報を取得
        AuthInfo authInfo = (AuthInfo) auth.getPrincipal();

        // seqLoginIdからtokenを設定してヘッダにセットする
        String token = Jwts.builder()
                // usernameだけを設定する
                .setSubject(authInfo.getUsername())
                // 8時間とする
                .setExpiration(new Date(System.currentTimeMillis() + 28_800_000))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        res.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);
    }

}
