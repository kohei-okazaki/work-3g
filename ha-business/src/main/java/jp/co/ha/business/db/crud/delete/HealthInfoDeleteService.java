package jp.co.ha.business.db.crud.delete;

/**
 * 健康情報削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoDeleteService {

    /**
     * 指定された健康情報IDの健康情報を削除する
     *
     * @param healthInfoId
     *     健康情報ID
     */
    void deleteByUserId(Integer healthInfoId);
}
