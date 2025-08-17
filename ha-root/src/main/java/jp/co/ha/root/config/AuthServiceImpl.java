package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.root.contents.auth.AuthInfo;

/**
 * 認証サービス実装クラス<br>
 * SpringSecurityで使用する
 *
 * @version 1.0.0
 */
@Service("authService")
public class AuthServiceImpl implements UserDetailsService {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
    /** 管理者サイトユーザログイン情報検索サービス */
    @Autowired
    private RootLoginInfoSearchService searchService;

    @Override
    public AuthInfo loadUserByUsername(String seqLoginId) throws UsernameNotFoundException {

        // ログインIDの妥当性チェック実施
        validate(seqLoginId);

        // 管理者サイトユーザログイン情報を検索
        RootLoginInfo rootLoginInfo = searchService
                .findById(Long.valueOf(seqLoginId))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "指定したログインIDは登録されていません. ログインID:" + Integer.valueOf(seqLoginId)));

        AuthInfo authInfo = new AuthInfo();
        BeanUtil.copy(rootLoginInfo, authInfo);

        return authInfo;
    }

    /**
     * 指定したログインIDの妥当性チェックを行う
     *
     * @param seqLoginId
     *     ログインID
     * @throws UsernameNotFoundException
     *     ログインIDが不正の場合
     */
    private void validate(String seqLoginId) throws UsernameNotFoundException {

        if (StringUtil.isEmpty(seqLoginId)) {
            LOG.warn("ログインIDが未指定です. ログインID:" + seqLoginId);
            throw new UsernameNotFoundException("ログインIDが未指定です. ログインID:" + seqLoginId);
        } else if (!RegexType.HALF_NUMBER.is(seqLoginId)) {
            // ログインIDが半角数字でない場合
            LOG.warn("ログインIDが半角数字ではありません. ログインID:" + seqLoginId);
            throw new UsernameNotFoundException("ログインIDが半角数字ではありません. ログインID:" + seqLoginId);
        }

    }

}
