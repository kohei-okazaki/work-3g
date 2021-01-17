package jp.co.ha.db.mapper.composite;

import java.util.List;

import jp.co.ha.db.entity.composite.CompositeAccount;
import jp.co.ha.db.entity.composite.CompositeAccountKey;

/**
 * アカウント情報と健康情報ファイル設定の複合Mapper
 *
 * @version 1.0.0
 */
public interface CompositeAccountMapper {

    /**
     * アカウント情報と健康情報ファイル設定の複合Entityを返す
     *
     * @param key
     *     ユーザID
     * @return アカウント情報と健康情報ファイル設定の複合Entity
     */
    CompositeAccount selectByPrimaryKey(CompositeAccountKey key);

    /**
     * アカウント情報と健康情報ファイル設定の複合Entityのリストを返す
     *
     * @return アカウント情報と健康情報ファイル設定の複合Entityのリスト
     */
    List<CompositeAccount> selectAll();

}
