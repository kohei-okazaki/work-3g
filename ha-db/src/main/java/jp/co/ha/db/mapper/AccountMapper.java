package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountExample;
import jp.co.ha.db.entity.AccountKey;

/**
 * アカウント情報Mapper
 *
 * @version 1.0.0
 */
public interface AccountMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    long countByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int deleteByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int deleteByPrimaryKey(AccountKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int insert(Account row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int insertSelective(Account row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    List<Account> selectByExampleWithRowbounds(AccountExample example,
            RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    List<Account> selectByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    Account selectByPrimaryKey(AccountKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int updateByExampleSelective(@Param("row") Account row,
            @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int updateByExample(@Param("row") Account row,
            @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int updateByPrimaryKeySelective(Account row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table account
     * 
     * @mbg.generated Sun Dec 15 11:23:03 GMT+09:00 2024
     */
    int updateByPrimaryKey(Account row);
}