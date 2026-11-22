package jp.co.ha.business.db.crud.read.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報の検索・件数取得が有効な情報に限定されることを、生成SQLとバインド値で確認する。
 */
class HealthInfoSearchServiceImplTest {

    private HealthInfoMapper mapper;
    private HealthInfoSearchServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(HealthInfoMapper.class);
        service = new HealthInfoSearchServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
    }

    @Test
    void findsLatestUndeletedHealthInfoForSpecifiedUser() {
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setSeqHealthInfoId(100L);
        healthInfo.setSeqUserId(42L);
        healthInfo.setDeleteFlag(false);
        when(mapper.selectByExample(any(HealthInfoExample.class)))
                .thenReturn(List.of(healthInfo));

        assertSame(healthInfo, service.findLatestBySeqUserId(42L).orElseThrow());

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.endsWith("WHERE ( SEQ_USER_ID = ? AND DELETE_FLAG = ? ) ORDER BY SEQ_HEALTH_INFO_ID DESC LIMIT 1"), sql),
                () -> assertEquals(List.of(42L, false), boundValues(boundSql)));
    }

    @Test
    void returnsEmptyWhenUserHasNoUndeletedHealthInfo() {
        when(mapper.selectByExample(any(HealthInfoExample.class)))
                .thenReturn(List.of());

        assertTrue(service.findLatestBySeqUserId(42L).isEmpty());
        verify(mapper).selectByExample(any(HealthInfoExample.class));
    }

    @Test
    void userSearchExcludesDeletedHealthInfoAndKeepsOrderingAndLimit() {
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setDeleteFlag(false);
        List<HealthInfo> healthInfos = List.of(healthInfo);
        when(mapper.selectByExample(any(HealthInfoExample.class)))
                .thenReturn(healthInfos);
        SelectOption option = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC).limit(2).build();

        assertSame(healthInfos, service.findBySeqUserId(42L, option));

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.endsWith("WHERE ( SEQ_USER_ID = ? AND DELETE_FLAG = ? ) ORDER BY SEQ_HEALTH_INFO_ID DESC LIMIT 2"), sql),
                () -> assertEquals(List.of(42L, false), boundValues(boundSql)));
    }

    @Test
    void periodSearchFiltersActiveInformationBeforePaging() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = from.plusMonths(1);
        SelectOption option = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC)
                .pageable(PageRequest.of(2, 10)).build();

        service.findBySeqUserIdBetweenHealthInfoRegDate(42L, from, to, option);

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("WHERE ( SEQ_USER_ID = ? AND HEALTH_INFO_REG_DATE BETWEEN ? AND ? AND DELETE_FLAG = ? )"), sql),
                () -> assertTrue(sql.endsWith("ORDER BY HEALTH_INFO_REG_DATE DESC LIMIT ?, ?"), sql),
                () -> assertEquals(List.of(42L, from, to, false, 20L, 10), boundValues(boundSql)));
    }

    @Test
    void periodSearchStillFiltersActiveInformationWhenUserIsNotSpecified() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = from.plusMonths(1);
        SelectOption option = new SelectOptionBuilder()
                .orderBy("HEALTH_INFO_REG_DATE", SortType.ASC).build();

        service.findBySeqUserIdBetweenHealthInfoRegDate(null, from, to, option);

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("WHERE ( HEALTH_INFO_REG_DATE BETWEEN ? AND ? AND DELETE_FLAG = ? )"), sql),
                () -> assertFalse(sql.contains("SEQ_USER_ID = ?"), sql),
                () -> assertEquals(List.of(from, to, false), boundValues(boundSql)));
    }

    @Test
    void periodCountOnlyCountsActiveInformationForTheUser() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = from.plusDays(1);

        service.countBySeqUserIdBetweenHealthInfoRegDate(42L, from, to);

        BoundSql boundSql = countedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.startsWith("SELECT COUNT(*) FROM HEALTH_INFO WHERE"), sql),
                () -> assertTrue(sql.contains("SEQ_USER_ID = ? AND HEALTH_INFO_REG_DATE BETWEEN ? AND ? AND DELETE_FLAG = ?"), sql),
                () -> assertEquals(List.of(42L, from, to, false), boundValues(boundSql)));
    }

    @Test
    void idSearchOnlyReturnsActiveInformationOwnedByTheUser() {
        service.findByHealthInfoIdAndSeqUserId(100L, 42L);

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("WHERE ( SEQ_HEALTH_INFO_ID = ? AND SEQ_USER_ID = ? AND DELETE_FLAG = ? )"), sql),
                () -> assertEquals(List.of(100L, 42L, false), boundValues(boundSql)));
    }

    @ParameterizedTest
    @CsvSource(value = { "100,42", "100,null", "null,42", "null,null" }, nullValues = "null")
    void idCountOnlyCountsActiveInformationEvenWhenIdsAreOmitted(Long healthInfoId,
            Long userId) {
        service.countByHealthInfoIdAndSeqUserId(healthInfoId, userId);

        BoundSql boundSql = countedBoundSql();
        String sql = normalizedSql(boundSql);
        List<Object> expectedValues = new ArrayList<>();
        if (healthInfoId != null) {
            expectedValues.add(healthInfoId);
        }
        if (userId != null) {
            expectedValues.add(userId);
        }
        expectedValues.add(false);
        assertAll(
                () -> assertTrue(sql.startsWith("SELECT COUNT(*) FROM HEALTH_INFO WHERE"), sql),
                () -> assertTrue(sql.contains("DELETE_FLAG = ?"), sql),
                () -> assertEquals(healthInfoId != null, sql.contains("SEQ_HEALTH_INFO_ID = ?")),
                () -> assertEquals(userId != null, sql.contains("SEQ_USER_ID = ?")),
                () -> assertEquals(expectedValues, boundValues(boundSql)));
    }

    @Test
    void previousIdSearchOnlyUsesActiveInformationAndKeepsSelectionRules() {
        when(mapper.selectByExample(any(HealthInfoExample.class)))
                .thenReturn(List.of());
        SelectOption option = new SelectOptionBuilder()
                .orderBy("SEQ_HEALTH_INFO_ID", SortType.DESC).limit(1).build();

        assertNull(service.findBySeqUserIdAndLowerSeqHealthInfoId(100L, 42L, option));

        BoundSql boundSql = selectedBoundSql();
        String sql = normalizedSql(boundSql);
        assertAll(
                () -> assertTrue(sql.contains("WHERE ( SEQ_HEALTH_INFO_ID < ? AND SEQ_USER_ID = ? AND DELETE_FLAG = ? )"), sql),
                () -> assertTrue(sql.endsWith("ORDER BY SEQ_HEALTH_INFO_ID DESC LIMIT 1"), sql),
                () -> assertEquals(List.of(100L, 42L, false), boundValues(boundSql)));
    }

    private BoundSql selectedBoundSql() {
        ArgumentCaptor<HealthInfoExample> captor = ArgumentCaptor
                .forClass(HealthInfoExample.class);
        verify(mapper).selectByExample(captor.capture());
        return boundSql("selectByExample", captor.getValue());
    }

    private BoundSql countedBoundSql() {
        ArgumentCaptor<HealthInfoExample> captor = ArgumentCaptor
                .forClass(HealthInfoExample.class);
        verify(mapper).countByExample(captor.capture());
        return boundSql("countByExample", captor.getValue());
    }

    private BoundSql boundSql(String statement, HealthInfoExample example) {
        Configuration configuration = new Configuration();
        configuration.addMapper(HealthInfoMapper.class);
        return configuration
                .getMappedStatement(HealthInfoMapper.class.getName() + "." + statement)
                .getBoundSql(example);
    }

    private String normalizedSql(BoundSql boundSql) {
        return boundSql.getSql().replaceAll("\\s+", " ").trim()
                .toUpperCase(Locale.ROOT);
    }

    private List<Object> boundValues(BoundSql boundSql) {
        return boundSql.getParameterMappings().stream()
                .map(ParameterMapping::getValue)
                .toList();
    }

}
