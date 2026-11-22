package jp.co.ha.business.db.crud.read.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.business.cache.BmiRangeMtCacheComponent;
import jp.co.ha.business.cache.RootRoleMtCacheComponent;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.mapper.ApiLogMapper;
import jp.co.ha.db.mapper.BmiRangeMtMapper;
import jp.co.ha.db.mapper.InquiryStatusMtMapper;
import jp.co.ha.db.mapper.InquiryTypeMtMapper;
import jp.co.ha.db.mapper.NewsInfoMapper;
import jp.co.ha.db.mapper.RootLoginInfoMapper;
import jp.co.ha.db.mapper.RootRoleMtMapper;
import jp.co.ha.db.mapper.RootUserNoteInfoMapper;
import jp.co.ha.db.mapper.UserMapper;
import jp.co.ha.db.mapper.UserRecoveryTokenMapper;

/**
 * 実サービスからMapperへ渡された条件をSQLへ展開し、有効条件と既存の検索範囲を検証する。
 */
class ActiveRecordSearchServiceTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2030, 1, 2, 12, 0);

    private record SearchCase(String name, Class<?> service, Class<?> mapper,
            String mapperField, String statement, Function<Object, Object> search,
            List<Object> values, String sqlFragment) {
        @Override
        public String toString() {
            return name;
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("searches")
    void everySearchUsesActiveRecordsAndPreservesItsOtherConditions(SearchCase test)
            throws Exception {
        Object service = test.service().getDeclaredConstructor().newInstance();
        AtomicReference<Object> parameter = new AtomicReference<>();
        Object mapper = mock(test.mapper(), invocation -> {
            if (invocation.getMethod().getName().equals(test.statement())) {
                parameter.set(invocation.getArgument(0));
                return test.statement().equals("countByExample") ? 0L : List.of();
            }
            return RETURNS_DEFAULTS.answer(invocation);
        });
        ReflectionTestUtils.setField(service, test.mapperField(), mapper);
        if (service instanceof BmiRangeMtSearchServiceImpl) {
            ReflectionTestUtils.setField(service, "cacheComponent", new BmiRangeMtCacheComponent());
        } else if (service instanceof RootRoleMtSearchServiceImpl) {
            ReflectionTestUtils.setField(service, "cacheComponent", new RootRoleMtCacheComponent());
        }

        try (MockedStatic<DateTimeUtil> clock = mockStatic(DateTimeUtil.class)) {
            clock.when(DateTimeUtil::getSysDate).thenReturn(NOW);
            Object result = test.search().apply(service);
            if (result instanceof Optional<?> optional) {
                assertTrue(optional.isEmpty(), "No valid row must produce an empty result");
            }
        }

        assertNotNull(parameter.get(), "The expected mapper search must be called");
        Configuration configuration = new Configuration();
        configuration.addMapper(test.mapper());
        BoundSql boundSql = configuration
                .getMappedStatement(test.mapper().getName() + "." + test.statement())
                .getBoundSql(parameter.get());
        String sql = boundSql.getSql().replaceAll("\\s+", " ").trim()
                .toUpperCase(Locale.ROOT);
        List<Object> values = boundSql.getParameterMappings().stream()
                .map(ParameterMapping::getValue).toList();
        assertAll(
                () -> assertTrue(sql.contains(test.sqlFragment()), sql),
                () -> assertTrue(sql.contains("DELETE_FLAG = ?"), sql),
                () -> assertEquals(test.values(), values));
    }

    static Stream<SearchCase> searches() {
        return Stream.of(
                query("user by ID", UserSearchServiceImpl.class, UserMapper.class, "mapper",
                        s -> ((UserSearchServiceImpl) s).findById(42L),
                        List.of(42L, false), "WHERE ( SEQ_USER_ID = ? AND DELETE_FLAG = ? )"),
                count("user count by ID", UserSearchServiceImpl.class, UserMapper.class,
                        s -> ((UserSearchServiceImpl) s).countBySeqUserId(42L),
                        List.of(42L, false), "WHERE ( SEQ_USER_ID = ? AND DELETE_FLAG = ? )"),
                count("user total", UserSearchServiceImpl.class, UserMapper.class,
                        s -> ((UserSearchServiceImpl) s).countBySeqUserId(null),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("administrator login", RootLoginInfoSearchServiceImpl.class,
                        RootLoginInfoMapper.class, "mapper",
                        s -> ((RootLoginInfoSearchServiceImpl) s).findById(42L),
                        List.of(42L, false), "WHERE ( SEQ_ROOT_LOGIN_INFO_ID = ? AND DELETE_FLAG = ? )"),
                query("note by ID", RootUserNoteInfoSearchServiceImpl.class,
                        RootUserNoteInfoMapper.class, "mapper",
                        s -> ((RootUserNoteInfoSearchServiceImpl) s).findById(42L),
                        List.of(42L, false), "WHERE ( SEQ_ROOT_USER_NOTE_INFO_ID = ? AND DELETE_FLAG = ? )"),
                count("note owner count", RootUserNoteInfoSearchServiceImpl.class,
                        RootUserNoteInfoMapper.class,
                        s -> ((RootUserNoteInfoSearchServiceImpl) s).countBySeqLoginId(42L),
                        List.of(42L, false), "WHERE ( SEQ_ROOT_LOGIN_INFO_ID = ? AND DELETE_FLAG = ? )"),
                count("note total", RootUserNoteInfoSearchServiceImpl.class,
                        RootUserNoteInfoMapper.class,
                        s -> ((RootUserNoteInfoSearchServiceImpl) s).countBySeqLoginId(null),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("news by ID", NewsInfoSearchServiceImpl.class, NewsInfoMapper.class, "mapper",
                        s -> ((NewsInfoSearchServiceImpl) s).findById(42L),
                        List.of(42L, false), "WHERE ( SEQ_NEWS_INFO_ID = ? AND DELETE_FLAG = ? )"),
                query("inquiry type order", InquiryTypeMtSearchServiceImpl.class,
                        InquiryTypeMtMapper.class, "inquiryTypeMtMapper",
                        s -> ((InquiryTypeMtSearchServiceImpl) s).findAll(option("DISP_ORDER", false)),
                        List.of(false), "WHERE ( DELETE_FLAG = ? ) ORDER BY DISP_ORDER DESC"),
                query("inquiry status order", InquiryStatusMtSearchServiceImpl.class,
                        InquiryStatusMtMapper.class, "inquiryStatusMtMapper",
                        s -> ((InquiryStatusMtSearchServiceImpl) s).findAll(option("INQUIRY_STATUS", false)),
                        List.of(false), "WHERE ( DELETE_FLAG = ? ) ORDER BY INQUIRY_STATUS DESC"),
                query("API log page", ApiLogSearchServiceImpl.class, ApiLogMapper.class, "mapper",
                        s -> ((ApiLogSearchServiceImpl) s).findAll(option("SEQ_API_LOG_ID", true)),
                        List.of(false, 10L, 5), "WHERE ( DELETE_FLAG = ? ) ORDER BY SEQ_API_LOG_ID DESC LIMIT ?, ?"),
                count("API log ID count", ApiLogSearchServiceImpl.class, ApiLogMapper.class,
                        s -> ((ApiLogSearchServiceImpl) s).countBySeqApiLogId(42L),
                        List.of(42L, false), "WHERE ( SEQ_API_LOG_ID = ? AND DELETE_FLAG = ? )"),
                count("API log total", ApiLogSearchServiceImpl.class, ApiLogMapper.class,
                        s -> ((ApiLogSearchServiceImpl) s).countBySeqApiLogId(null),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("BMI range cache", BmiRangeMtSearchServiceImpl.class, BmiRangeMtMapper.class, "mapper",
                        s -> ((BmiRangeMtSearchServiceImpl) s).findAll(),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("role cache", RootRoleMtSearchServiceImpl.class, RootRoleMtMapper.class, "mapper",
                        s -> ((RootRoleMtSearchServiceImpl) s).findAll(),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("requested roles", RootRoleMtSearchServiceImpl.class, RootRoleMtMapper.class, "mapper",
                        s -> ((RootRoleMtSearchServiceImpl) s).findByRoles(List.of("00")),
                        List.of(false), "WHERE ( DELETE_FLAG = ? )"),
                query("recovery token validity", UserRecoveryTokenSearchServiceImpl.class,
                        UserRecoveryTokenMapper.class, "mapper",
                        s -> ((UserRecoveryTokenSearchServiceImpl) s)
                                .findBySeqUserIdAndTokenAndValidTokenCreateDate(42L, "token"),
                        List.of(42L, "token", NOW.minusHours(1), false),
                        "WHERE ( SEQ_USER_ID = ? AND TOKEN = ? AND TOKEN_CREATE_DATE > ? AND DELETE_FLAG = ? )"));
    }

    @ParameterizedTest
    @MethodSource("primarySearches")
    void nullPrimaryIdReturnsEmptyWithoutBroadeningTheQuery(SearchCase test) throws Exception {
        Object service = test.service().getDeclaredConstructor().newInstance();
        Object mapper = mock(test.mapper());
        ReflectionTestUtils.setField(service, test.mapperField(), mapper);

        Optional<?> result = ReflectionTestUtils.invokeMethod(service, "findById", (Object) null);

        assertTrue(result.isEmpty());
        verifyNoInteractions(mapper);
    }

    static Stream<SearchCase> primarySearches() {
        return searches().filter(test -> List.of("user by ID", "administrator login", "note by ID", "news by ID")
                .contains(test.name()));
    }

    private static SearchCase query(String name, Class<?> service, Class<?> mapper, String field,
            Function<Object, Object> search, List<Object> values, String sql) {
        return new SearchCase(name, service, mapper, field, "selectByExample", search, values, sql);
    }

    private static SearchCase count(String name, Class<?> service, Class<?> mapper,
            Function<Object, Object> search, List<Object> values, String sql) {
        return new SearchCase(name, service, mapper, "mapper", "countByExample", search, values, sql);
    }

    private static SelectOption option(String column, boolean page) {
        SelectOptionBuilder builder = new SelectOptionBuilder().orderBy(column, SortType.DESC);
        if (page) {
            builder.pageable(PageRequest.of(2, 5));
        }
        return builder.build();
    }
}
