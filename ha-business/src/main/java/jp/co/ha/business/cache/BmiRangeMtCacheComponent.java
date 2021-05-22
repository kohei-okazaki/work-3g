package jp.co.ha.business.cache;

import org.springframework.stereotype.Component;

import jp.co.ha.common.db.BaseOnmemoryCache;
import jp.co.ha.db.entity.BmiRangeMt;

/**
 * BMI範囲マスタのキャッシュComponent<br>
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
public class BmiRangeMtCacheComponent extends BaseOnmemoryCache<BmiRangeMt> {

    @Override
    protected long getExpireSeconds() {
        return 1000 * 60 * 60;
    }

    @Override
    protected String getTableName() {
        return "BMI_RANGE_MT";
    }

}
