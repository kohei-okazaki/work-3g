package jp.co.ha.db.mapper.composite;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.composite.CompositeHealthInfo;
import jp.co.ha.db.entity.composite.CompositeHealthInfoKey;

/**
 * 健康情報とBMI範囲マスタの複合Mapper
 *
 * @version 1.0.0
 */
public interface CompositeHealthInfoMapper {

    /**
     * 健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @param key
     *     健康情報とBMI範囲マスタの複合Entityキー
     * @return 健康情報とBMI範囲マスタの複合Entity
     */
    CompositeHealthInfo selectByPrimaryKey(CompositeHealthInfoKey key);

    /**
     * 健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @param example
     *     {@linkplain HealthInfoExample}
     * @param rowBounds
     *     {@linkplain RowBounds}
     * @return 健康情報とBMI範囲マスタの複合Entityのリスト
     */
    List<CompositeHealthInfo> selectAll(HealthInfoExample example, RowBounds rowBounds);

}
