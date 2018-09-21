package jp.co.ha.business.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.entity.AccountExample;

public interface AccountMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	long countByExample(AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int deleteByExample(AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int deleteByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int insert(Account record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int insertSelective(Account record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	List<Account> selectByExampleWithBLOBs(AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	List<Account> selectByExample(AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	Account selectByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByExampleWithBLOBs(@Param("record") Account record, @Param("example") AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByPrimaryKeySelective(Account record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByPrimaryKeyWithBLOBs(Account record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	int updateByPrimaryKey(Account record);
}