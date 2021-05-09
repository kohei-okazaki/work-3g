package jp.co.ha.business.db.crud.delete;

/**
 * お知らせ情報削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface NewsInfoDeleteService {

    /**
     * 指定したお知らせ情報IDのお知らせ情報を削除する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     */
    void delete(Long seqNewsInfoId);

}
