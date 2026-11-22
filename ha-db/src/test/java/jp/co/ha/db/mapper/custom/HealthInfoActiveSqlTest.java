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
import org.springframework.data.domain.PageRequest;

import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.custom.CompositeHealthInfoKey;

/**
 * カスタム検索SQLが削除済み健康情報を除外し、所有者・期間・ページング条件を維持することを確認する。
 */
class HealthInfoActiveSqlTest {

    @Test
    void detailSearchRequiresAnActiveRecordAndBothIds() {
        CompositeHealthInfoKey key = new CompositeHealthInfoKey();
        key.setSeqUserId(42L);
        key.setSeqHealthInfoId(100L);

        BoundSql boundSql = boundSql(CompositeHealthInfoMapper.class,
                "selectByPrimaryKey", key);
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("LEFT OUTER JOIN BMI_RANGE_MT"), sql),
                () -> assertTrue(sql.endsWith("WHERE HI.SEQ_USER_ID = ? AND HI.SEQ_HEALTH_INFO_ID = ? AND HI.DELETE_FLAG = FALSE"), sql),
                () -> assertEquals(List.of("seqUserId", "seqHealthInfoId"), parameters(boundSql)));
    }

    @Test
    void administratorListFiltersActiveRecordsBeforePaging() {
        HealthInfoExample example = new HealthInfoExample();
        example.setOrderByClause("HI.SEQ_HEALTH_INFO_ID DESC");
        example.setPageable(PageRequest.of(2, 10));

        BoundSql boundSql = boundSql(CompositeHealthInfoMapper.class, "selectAll", example);
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.endsWith("WHERE HI.DELETE_FLAG = FALSE ORDER BY HI.SEQ_HEALTH_INFO_ID DESC LIMIT ?, ?"), sql),
                () -> assertEquals(List.of("pageable.offset", "pageable.pageSize"), parameters(boundSql)));
    }

    @Test
    void administratorListWithoutPagingAlsoExcludesDeletedRecords() {
        BoundSql boundSql = boundSql(CompositeHealthInfoMapper.class, "selectAll",
                new HealthInfoExample());

        assertTrue(normalizedSql(boundSql).endsWith("WHERE HI.DELETE_FLAG = FALSE"));
    }

    @Test
    void monthlyGraphFiltersBothTheCountAndTheOuterDateSelection() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        BoundSql boundSql = boundSql(CompositeMonthlyMapper.class,
                "selectByHealthInfoRegDate", Map.of("from", from, "to", from.plusMonths(1)));
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("FROM HEALTH_INFO WHERE DELETE_FLAG = FALSE GROUP BY"), sql),
                () -> assertTrue(sql.contains("WHERE HI.HEALTH_INFO_REG_DATE BETWEEN ? AND ? AND HI.DELETE_FLAG = FALSE AND"), sql),
                () -> assertEquals(List.of("from", "to"), parameters(boundSql)));
    }

    @Test
    void batchReaderFiltersActiveRecordsWithinThePeriodBeforePaging() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        BoundSql boundSql = boundSql(PagingHealthInfoMapper.class,
                "selectByHealthInfoRegDate", Map.of("from", from, "to", from.plusDays(1),
                        "_skiprows", 100, "_pagesize", 50));
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.endsWith("WHERE HEALTH_INFO_REG_DATE BETWEEN ? AND ? AND DELETE_FLAG = FALSE ORDER BY SEQ_HEALTH_INFO_ID ASC LIMIT ?, ?"), sql),
                () -> assertEquals(List.of("from", "to", "_skiprows", "_pagesize"), parameters(boundSql)));
    }

    private BoundSql boundSql(Class<?> mapper, String statement, Object parameter) {
        Configuration configuration = new Configuration();
        configuration.addMapper(mapper);
        return configuration.getMappedStatement(mapper.getName() + "." + statement)
                .getBoundSql(parameter);
    }

    private String normalizedSql(BoundSql boundSql) {
        return boundSql.getSql().replaceAll("\\s+", " ").trim()
                .toUpperCase(Locale.ROOT);
    }

    private List<String> parameters(BoundSql boundSql) {
        return boundSql.getParameterMappings().stream()
                .map(ParameterMapping::getProperty).toList();
    }

}
