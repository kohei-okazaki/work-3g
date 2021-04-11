package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootLoginInfoExample;
import jp.co.ha.db.entity.RootLoginInfoKey;

/**
 * 管理者サイトユーザログイン情報Mapper
 *
 * @version 1.0.0
 */
public interface RootLoginInfoMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    long countByExample(RootLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByExample(RootLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int deleteByPrimaryKey(RootLoginInfoKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insert(RootLoginInfo record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int insertSelective(RootLoginInfo record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootLoginInfo> selectByExampleWithRowbounds(RootLoginInfoExample example,
            RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    List<RootLoginInfo> selectByExample(RootLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    RootLoginInfo selectByPrimaryKey(RootLoginInfoKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExampleSelective(@Param("record") RootLoginInfo record,
            @Param("example") RootLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByExample(@Param("record") RootLoginInfo record,
            @Param("example") RootLoginInfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKeySelective(RootLoginInfo record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sat Apr 10 13:45:33 GMT+09:00 2021
     */
    int updateByPrimaryKey(RootLoginInfo record);
}