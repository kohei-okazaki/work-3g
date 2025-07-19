package jp.co.ha.db.mapper.composite;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.UserExample;
import jp.co.ha.db.entity.composite.CompositeUser;
import jp.co.ha.db.entity.composite.CompositeUserKey;

/**
 * ユーザ情報と健康情報ファイル設定の複合Mapper
 *
 * @version 1.0.0
 */
public interface CompositeUserMapper {

    /**
     * ユーザ情報と健康情報ファイル設定の複合Entityを返す
     *
     * @param key
     *     ユーザID
     * @return ユーザ情報と健康情報ファイル設定の複合Entity
     */
    CompositeUser selectByPrimaryKey(CompositeUserKey key);

    /**
     * ユーザ情報と健康情報ファイル設定の複合Entityのリストを返す
     *
     * @param example
     *     {@linkplain UserExample}
     * @param rowBounds
     *     {@linkplain RowBounds}
     * @return ユーザ情報と健康情報ファイル設定の複合Entityのリスト
     */
    List<CompositeUser> selectAll(UserExample example, RowBounds rowBounds);

}
