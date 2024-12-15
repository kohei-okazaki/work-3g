package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.HealthInfoFileSettingExample;
import jp.co.ha.db.entity.HealthInfoFileSettingKey;

/**
 * 健康情報ファイル設定情報Mapper
 *
 * @version 1.0.0
 */
public interface HealthInfoFileSettingMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    long countByExample(HealthInfoFileSettingExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int deleteByExample(HealthInfoFileSettingExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int deleteByPrimaryKey(HealthInfoFileSettingKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int insert(HealthInfoFileSetting row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int insertSelective(HealthInfoFileSetting row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    List<HealthInfoFileSetting> selectByExampleWithRowbounds(
            HealthInfoFileSettingExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    List<HealthInfoFileSetting> selectByExample(HealthInfoFileSettingExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    HealthInfoFileSetting selectByPrimaryKey(HealthInfoFileSettingKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int updateByExampleSelective(@Param("row") HealthInfoFileSetting row,
            @Param("example") HealthInfoFileSettingExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int updateByExample(@Param("row") HealthInfoFileSetting row,
            @Param("example") HealthInfoFileSettingExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int updateByPrimaryKeySelective(HealthInfoFileSetting row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info_file_setting
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    int updateByPrimaryKey(HealthInfoFileSetting row);
}