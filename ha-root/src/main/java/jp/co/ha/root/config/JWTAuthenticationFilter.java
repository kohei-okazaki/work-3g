package jp.co.ha.root.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.crypto.SecretKey;

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
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.root.contents.auth.AuthInfo;
import jp.co.ha.root.contents.auth.request.LoginApiRequest;

/**
 * 認証フィルター
 *
 * @version 1.0.0
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /** 秘密鍵 */
    static final String SECRET = "qnhHdE4Opwnxm1RM1g/KHZQx48WnQFbQsOngdF+ZUFuvJPCL1lkyE7vDh+A9WwMVaqVRLJDGMBr1QOKGWyyWwg==";
    /** 署名・検証で共通に使うキー（HS512 用に十分長いキーを SecretKey に変換） */
    static final SecretKey SIGNING_KEY = Keys
            .hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
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
    private static final HttpMethod LOGIN_API_METHOD = HttpMethod.POST;
    /** 要求パラメータ:ログインID */
    private static final String REQ_PARAM_SEQ_LOGIN_ID = "seq_login_id";
    /** 要求パラメータ:パスワード */
    private static final String REQ_PARAM_PASSWORD = "password";

    /** 認証情報管理クラス */
    private AuthenticationManager authenticationManager;
    /** パスワードEncoder */
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
        setRequiresAuthenticationRequestMatcher(PathPatternRequestMatcher
                .withDefaults()
                .matcher(LOGIN_API_METHOD, LOGIN_API_PATH));

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
        // システム日付
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        // 有効期限
        LocalDateTime expire = sysdate.plusHours(8l);

        String token = Jwts.builder()
                .subject(authInfo.getUsername())
                .issuedAt(DateTimeUtil.toDate(sysdate))
                .expiration(DateTimeUtil.toDate(expire))
                .signWith(SIGNING_KEY)
                .compact();

        res.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);
    }

}
