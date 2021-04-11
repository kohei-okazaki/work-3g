package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.db.entity.RootRoleMtExample;
import jp.co.ha.db.entity.RootRoleMtKey;

/**
 * 管理者サイト権限マスタMapper
 *
 * @version 1.0.0
 */
public interface RootRoleMtMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    long countByExample(RootRoleMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByExample(RootRoleMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByPrimaryKey(RootRoleMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insert(RootRoleMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insertSelective(RootRoleMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootRoleMt> selectByExampleWithRowbounds(RootRoleMtExample example,
            RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootRoleMt> selectByExample(RootRoleMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    RootRoleMt selectByPrimaryKey(RootRoleMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExampleSelective(@Param("record") RootRoleMt record,
            @Param("example") RootRoleMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExample(@Param("record") RootRoleMt record,
            @Param("example") RootRoleMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKeySelective(RootRoleMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_role_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKey(RootRoleMt record);
}