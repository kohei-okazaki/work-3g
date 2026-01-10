package jp.co.ha.common.crypt;

import static jp.co.ha.common.aws.AwsSystemsManagerComponent.*;
import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.common.aws.AwsSystemsManagerComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.StringUtil;

/**
 * シーザー可逆暗号化クラス
 *
 * @version 1.0.0
 */
@Component("caesarCrypter")
public class CaesarCrypter implements Crypter {

    /** Systems Manager */
    @Autowired
    private AwsSystemsManagerComponent ssm;

    @Override
    public String encrypt(String str) {

        if (StringUtil.isEmpty(str)
                || !Pattern.matches(RegexType.HALF_ALPHABET.getValue(), str)) {
            // null or 空文字 or 半角英字以外の場合
            return str;
        }

        String result = "";
        try {
            for (int i = 0; i < str.length(); i++) {
                int shifted = str.charAt(i);
                char c = (char) (shifted
                        + Integer.valueOf(ssm.getValue(KEY_CRYPT_SHIFT)));
                if (c > 'z') {
                    // zより後ろの文字列の場合、aに戻す為26引く
                    result += (char) (c - 26);
                } else {
                    result += c;
                }
            }
        } catch (BaseException e) {
            throw new SystemRuntimeException(AWS_SSM_GET_ERROR, e);
        }
        return result;
    }

    @Override
    public String decrypt(String str) {

        if (StringUtil.isEmpty(str)
                || !Pattern.matches(RegexType.HALF_ALPHABET.getValue(), str)) {
            // null or 空文字 or 半角英字以外の場合
            return str;
        }

        String result = "";
        try {
            for (int i = 0; i < str.length(); i++) {
                int shifted = str.charAt(i);
                char c = (char) (shifted
                        - Integer.valueOf(ssm.getValue(KEY_CRYPT_SHIFT)));
                if (c < 'a') {
                    // zより後ろの文字列の場合、aに戻す為26引く
                    result += (char) (c + 26);
                } else {
                    result += c;
                }
            }
        } catch (BaseException e) {
            throw new SystemRuntimeException(AWS_SSM_GET_ERROR, e);
        }
        return result;
    }

}
