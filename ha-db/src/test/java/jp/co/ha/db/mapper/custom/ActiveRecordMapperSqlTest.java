package jp.co.ha.db.mapper.custom;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.PageRequest;

import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.InquiryManagementExample;
import jp.co.ha.db.entity.custom.CompositeHealthInfoKey;

/**
 * 結合・集計・バッチ抽出のSQLに有効条件が適用されることを確認する。
 */
class ActiveRecordMapperSqlTest {

    @Test
    void userGraphFiltersTheCountAndTheOuterDates() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        BoundSql boundSql = sql(CompositeMonthlyMapper.class, "selectUserByRegDate",
                Map.of("from", from, "to", from.plusMonths(1)));
        String sql = normalized(boundSql);

        assertAll(
                () -> assertTrue(sql.contains("FROM USER WHERE DELETE_FLAG = FALSE GROUP BY"), sql),
                () -> assertTrue(sql.contains("WHERE U.REG_DATE BETWEEN ? AND ? AND U.DELETE_FLAG = FALSE AND"), sql),
                () -> assertEquals(List.of("from", "to"), parameters(boundSql)));
    }

    @Test
    void administratorDetailsRequireBothAnActiveUserAndActiveRoles() {
        BoundSql boundSql = sql(CompositeRootUserInfoMapper.class,
                "selectBySeqRootLoginInfoId", 42L);
        String sql = normalized(boundSql);

        assertAll(
                () -> assertTrue(sql.contains("LI.SEQ_ROOT_LOGIN_INFO_ID = ?"), sql),
                () -> assertTrue(sql.contains("AND LI.DELETE_FLAG = FALSE AND RM.DELETE_FLAG = FALSE"), sql),
                () -> assertTrue(sql.endsWith("ORDER BY RM.SEQ_ROOT_ROLE_MT_ID"), sql),
                () -> assertEquals(List.of("seqRootLoginInfoId"), parameters(boundSql)));
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void inquiryListFiltersTheMainRowAndBothMastersBeforePaging(boolean paged) {
        InquiryManagementExample example = new InquiryManagementExample();
        if (paged) {
            example.setOrderByClause("IM.SEQ_INQUIRY_MNG_ID DESC");
            example.setPageable(PageRequest.of(2, 10));
        }
        BoundSql boundSql = sql(CompositeInquriyMapper.class, "selectAll", example);
        String sql = normalized(boundSql);

        assertAll(
                () -> assertTrue(sql.contains("AND SM.DELETE_FLAG = FALSE)"), sql),
                () -> assertTrue(sql.contains("AND TM.DELETE_FLAG = FALSE)"), sql),
                () -> assertTrue(sql.contains("WHERE IM.DELETE_FLAG = FALSE"), sql),
                () -> assertEquals(paged,
                        sql.endsWith("ORDER BY IM.SEQ_INQUIRY_MNG_ID DESC LIMIT ?, ?")),
                () -> assertEquals(paged ? List.of("pageable.offset", "pageable.pageSize") : List.of(),
                        parameters(boundSql)));
    }

    @Test
    void dailyApiLogExtractionFiltersActiveRowsWithinItsPeriodBeforePaging() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        BoundSql boundSql = sql(PagingApiLogMapper.class, "selectByRegDate",
                Map.of("from", from, "to", from.plusDays(1), "_skiprows", 50, "_pagesize", 25));
        String sql = normalized(boundSql);

        assertAll(
                () -> assertTrue(sql.endsWith("WHERE REG_DATE BETWEEN ? AND ? AND DELETE_FLAG = FALSE ORDER BY REG_DATE ASC LIMIT ?, ?"), sql),
                () -> assertEquals(List.of("from", "to", "_skiprows", "_pagesize"), parameters(boundSql)));
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void inactiveBmiMasterIsExcludedInTheJoinWithoutDroppingTheHealthRecord(boolean list) {
        Object parameter;
        if (list) {
            parameter = new HealthInfoExample();
        } else {
            CompositeHealthInfoKey key = new CompositeHealthInfoKey();
            key.setSeqHealthInfoId(100L);
            key.setSeqUserId(42L);
            parameter = key;
        }
        String sql = normalized(sql(CompositeHealthInfoMapper.class,
                list ? "selectAll" : "selectByPrimaryKey", parameter));
        String where = sql.substring(sql.indexOf(" WHERE "));

        assertAll(
                () -> assertTrue(sql.contains("LEFT OUTER JOIN BMI_RANGE_MT BRM ON HI.SEQ_BMI_RANGE_MT_ID = BRM.SEQ_BMI_RANGE_MT_ID AND BRM.DELETE_FLAG = FALSE"), sql),
                () -> assertTrue(where.contains("HI.DELETE_FLAG = FALSE"), sql),
                () -> assertFalse(where.contains("BRM.DELETE_FLAG"), sql));
    }

    private BoundSql sql(Class<?> mapper, String statement, Object parameter) {
        Configuration configuration = new Configuration();
        configuration.addMapper(mapper);
        return configuration.getMappedStatement(mapper.getName() + "." + statement)
                .getBoundSql(parameter);
    }

    private String normalized(BoundSql boundSql) {
        return boundSql.getSql().replaceAll("\\s+", " ").trim().toUpperCase(Locale.ROOT);
    }

    private List<String> parameters(BoundSql boundSql) {
        return boundSql.getParameterMappings().stream().map(ParameterMapping::getProperty).toList();
    }
}
