package jp.co.ha.db.mapper;

import jp.co.ha.db.entity.CompositAccount;

/**
 * アカウント情報とメール情報と健康情報ファイル設定の複合Mapper
 *
 * @version 1.0.0
 */
public interface CompositAccountMapper {

    /**
     * アカウント情報とメール情報と健康情報ファイル設定の複合Entityを返す
     *
     * @param userId
     *     ユーザID
     * @return アカウント情報とメール情報と健康情報ファイル設定の複合Entity
     */
    CompositAccount selectByPrimaryKey(String userId);

}
