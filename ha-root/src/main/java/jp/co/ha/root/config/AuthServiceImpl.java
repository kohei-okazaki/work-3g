package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.RootLoginInfoSearchServiceImpl;
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
    /** {@linkplain RootLoginInfoSearchServiceImpl} */
    @Autowired
    private RootLoginInfoSearchService searchService;

    @Override
    public AuthInfo loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.debug("#valid start");
        // ログインIDの妥当性チェック実施
        validate(username);
        LOG.debug("#valid end");

        LOG.debug("#findById start");
        // 管理者サイトユーザログイン情報を検索
        RootLoginInfo rootLoginInfo = searchService
                .findById(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "指定したログインIDは登録されていません. ログインID:" + Integer.valueOf(username)));
        LOG.debug("#findById end");

        AuthInfo authInfo = new AuthInfo();
        BeanUtil.copy(rootLoginInfo, authInfo);

        return authInfo;
    }

    /**
     * 指定したログインIDの妥当性チェックを行う
     *
     * @param username
     *     ログインID
     * @throws UsernameNotFoundException
     *     ログインIDが不正の場合
     */
    private void validate(String username) throws UsernameNotFoundException {

        if (StringUtil.isEmpty(username)) {
            LOG.warn("ログインIDが未指定です. ログインID:" + username);
            throw new UsernameNotFoundException("ログインIDが未指定です. ログインID:" + username);
        } else if (!RegexType.HALF_NUMBER.is(username)) {
            // ログインIDが半角数字でない場合
            LOG.warn("ログインIDが半角数字ではありません. ログインID:" + username);
            throw new UsernameNotFoundException("ログインIDが半角数字ではありません. ログインID:" + username);
        }

    }

}
