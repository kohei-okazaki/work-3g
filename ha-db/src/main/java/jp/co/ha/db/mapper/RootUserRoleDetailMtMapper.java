package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.RootUserRoleDetailMt;
import jp.co.ha.db.entity.RootUserRoleDetailMtExample;
import jp.co.ha.db.entity.RootUserRoleDetailMtKey;

/**
 * 管理者サイトユーザ権限詳細マスタMapper
 *
 * @version 1.0.0
 */
public interface RootUserRoleDetailMtMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    long countByExample(RootUserRoleDetailMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByExample(RootUserRoleDetailMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByPrimaryKey(RootUserRoleDetailMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insert(RootUserRoleDetailMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insertSelective(RootUserRoleDetailMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootUserRoleDetailMt> selectByExampleWithRowbounds(
            RootUserRoleDetailMtExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootUserRoleDetailMt> selectByExample(RootUserRoleDetailMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    RootUserRoleDetailMt selectByPrimaryKey(RootUserRoleDetailMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExampleSelective(@Param("record") RootUserRoleDetailMt record,
            @Param("example") RootUserRoleDetailMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExample(@Param("record") RootUserRoleDetailMt record,
            @Param("example") RootUserRoleDetailMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKeySelective(RootUserRoleDetailMt record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKey(RootUserRoleDetailMt record);
}