package jp.co.ha.db.mapper.custom;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.UserExample;
import jp.co.ha.db.entity.UserKey;
import jp.co.ha.db.entity.custom.CompositeUser;

/**
 * 以下のユーザ関連テーブル情報の複合Mapper
 * <ul>
 * <li>user</li>
 * <li>health_info_file_setting</li>
 * <li>user_health_goal</li>
 * </ul>
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
    CompositeUser selectByPrimaryKey(UserKey key);

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

    /**
     * ページングされたユーザ情報リストを検索する
     * 
     * @return ユーザ情報リスト
     */
    List<CompositeUser> selectAnalysisTarget();

}
