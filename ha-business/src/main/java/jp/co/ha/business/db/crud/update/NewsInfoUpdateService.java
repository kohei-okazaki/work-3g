package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報更新サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface NewsInfoUpdateService {

    /**
     * お知らせ情報を更新する
     * 
     * @param entity
     *     お知らせ情報
     */
    void update(NewsInfo entity);

}
