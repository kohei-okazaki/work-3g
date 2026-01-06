package jp.co.ha.root.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import jp.co.ha.common.aws.AwsSystemsManagerComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * JWTの署名鍵を提供するProvider
 * 
 * @version 1.0.0
 */
@Component
public class JwtSigningKeyProvider {

    /** キー：鍵名 */
    private static final String KEY = "ROOT_JWT_SECRET";
    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(JwtSigningKeyProvider.class);

    /** SystemsManagerのComponent */
    @Autowired
    private AwsSystemsManagerComponent ssm;
    /** 鍵情報 */
    private SecretKey signingKey;

    /**
     * 鍵情報を返す
     * 
     * @return 鍵情報
     */
    public SecretKey get() {
        if (signingKey == null) {
            load();
        }
        return signingKey;
    }

    /**
     * 鍵情報を読み込む
     */
    private void load() {

        LOG.debug("key load.");
        try {
            String secret = ssm.getValue(KEY, true);
            signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (BaseException e) {
            throw new IllegalStateException("Failed to load JWT secret from SSM", e);
        }
    }
}
