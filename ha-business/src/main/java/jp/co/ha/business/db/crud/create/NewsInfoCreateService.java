package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報登録サービスインターフェース
 *
 * @version 1.0.0
 */
public interface NewsInfoCreateService {

    /**
     * お知らせ情報を登録する
     *
     * @param entity
     *     お知らせ情報
     */
    void create(NewsInfo entity);

}
