package jp.co.ha.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.BmiRangeMtExample;
import jp.co.ha.db.entity.BmiRangeMtKey;

/**
 * BMI範囲マスタMapper
 *
 * @version 1.0.0
 */
public interface BmiRangeMtMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    long countByExample(BmiRangeMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int deleteByExample(BmiRangeMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int deleteByPrimaryKey(BmiRangeMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int insert(BmiRangeMt row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int insertSelective(BmiRangeMt row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    List<BmiRangeMt> selectByExampleWithRowbounds(BmiRangeMtExample example,
            RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    List<BmiRangeMt> selectByExample(BmiRangeMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    BmiRangeMt selectByPrimaryKey(BmiRangeMtKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int updateByExampleSelective(@Param("row") BmiRangeMt row,
            @Param("example") BmiRangeMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int updateByExample(@Param("row") BmiRangeMt row,
            @Param("example") BmiRangeMtExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int updateByPrimaryKeySelective(BmiRangeMt row);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table bmi_range_mt
     * 
     * @mbg.generated Sat Dec 21 15:17:08 GMT+09:00 2024
     */
    int updateByPrimaryKey(BmiRangeMt row);
}