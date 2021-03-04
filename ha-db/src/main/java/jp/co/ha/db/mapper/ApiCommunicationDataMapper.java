package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.entity.ApiCommunicationDataExample;
import jp.co.ha.db.entity.ApiCommunicationDataKey;

/**
 * API通信情報Mapper
 *
 * @version 1.0.0
 */
public interface ApiCommunicationDataMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    long countByExample(ApiCommunicationDataExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int deleteByExample(ApiCommunicationDataExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int deleteByPrimaryKey(ApiCommunicationDataKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int insert(ApiCommunicationData record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int insertSelective(ApiCommunicationData record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    List<ApiCommunicationData> selectByExampleWithRowbounds(
            ApiCommunicationDataExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    List<ApiCommunicationData> selectByExample(ApiCommunicationDataExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    ApiCommunicationData selectByPrimaryKey(ApiCommunicationDataKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int updateByExampleSelective(@Param("record") ApiCommunicationData record,
            @Param("example") ApiCommunicationDataExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int updateByExample(@Param("record") ApiCommunicationData record,
            @Param("example") ApiCommunicationDataExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int updateByPrimaryKeySelective(ApiCommunicationData record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table api_communication_data
     *
     * @mbg.generated Sun Feb 07 12:27:35 GMT+09:00 2021
     */
    int updateByPrimaryKey(ApiCommunicationData record);
}