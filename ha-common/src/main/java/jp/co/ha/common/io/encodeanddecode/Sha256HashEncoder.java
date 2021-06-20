package jp.co.ha.common.io.encodeanddecode;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.Algorithm;

/**
 * SHA-256ハッシュ値作成クラス
 *
 * @version 1.0.0
 * @see jp.co.ha.common.io.encodeanddecode.annotation.Sha256
 */
@Component(Sha256HashEncoder.ID)
public class Sha256HashEncoder extends HashEncoder {

    /** BeanID */
    public static final String ID = "sha256HashEncoder";

    @Override
    protected Algorithm getAlgorithm() {
        return Algorithm.SHA_256;
    }
}
