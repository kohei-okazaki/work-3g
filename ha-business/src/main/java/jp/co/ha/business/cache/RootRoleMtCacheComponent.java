package jp.co.ha.business.cache;

import org.springframework.stereotype.Component;

import jp.co.ha.common.db.BaseOnmemoryCache;
import jp.co.ha.db.entity.RootRoleMt;

/**
 * 管理者サイト権限マスタのキャッシュComponent
 * <table>
 * <tr>
 * <th>キャッシュ有効期限</th>
 * <td>1時間</td>
 * </tr>
 * </table>
 *
 * @version 1.0.0
 */
@Component
public class RootRoleMtCacheComponent extends BaseOnmemoryCache<RootRoleMt> {

    @Override
    protected long getExpireSeconds() {
        return 1000 * 60 * 60;
    }

    @Override
    protected String getTableName() {
        return "ROOT_ROLE_MT";
    }

}
