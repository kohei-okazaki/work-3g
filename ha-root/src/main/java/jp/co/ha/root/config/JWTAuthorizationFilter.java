package jp.co.ha.root.config;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import jp.co.ha.common.util.StringUtil;

/**
 * 認可フィルター
 *
 * @version 1.0.0
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    /** {@linkplain AuthenticationManager} */
    @SuppressWarnings("unused")
    private AuthenticationManager authenticationManager;

    /**
     * コンストラクタ
     *
     * @param authenticationManager
     *     認証情報管理クラス
     */
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(JWTAuthenticationFilter.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(JWTAuthenticationFilter.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        // AuthorizationヘッダのBearer Prefixである場合
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * 認証情報を返す
     *
     * @param request
     *     HTTPリクエスト情報
     * @return 認証情報
     */
    private UsernamePasswordAuthenticationToken getAuthentication(
            HttpServletRequest request) {

        // tokenを取得
        String token = request.getHeader(JWTAuthenticationFilter.HEADER_AUTHORIZATION);

        if (StringUtil.isEmpty(token)) {
            // トークンが未指定の場合
            return null;
        }

        // parse the token.
        String user = Jwts.parser()
                .setSigningKey(JWTAuthenticationFilter.SECRET.getBytes())
                .parseClaimsJws(token.replace(JWTAuthenticationFilter.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        if (user == null) {
            // tokenをparseした結果nullの場合
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

}
