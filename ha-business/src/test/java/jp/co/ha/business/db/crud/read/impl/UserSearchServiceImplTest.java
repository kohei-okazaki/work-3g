package jp.co.ha.business.db.crud.read.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserExample;
import jp.co.ha.db.mapper.UserMapper;

/**
 * API利用判定の検索条件を、Mapperが生成するSQLとバインド値で確認する。
 */
class UserSearchServiceImplTest {

    @ParameterizedTest
    @ValueSource(strings = { "2030-01-02T00:00:00", "2030-01-02T23:59:59" })
    void onlySearchesUndeletedUsersWhosePasswordIsValidToday(String systemDateTime) {
        UserMapper mapper = mock(UserMapper.class);
        UserSearchServiceImpl service = new UserSearchServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
        User user = new User();
        user.setSeqUserId(1L);
        when(mapper.selectByExample(any(UserExample.class))).thenReturn(List.of(user));

        LocalDateTime now = LocalDateTime.parse(systemDateTime);
        try (MockedStatic<DateTimeUtil> dateTime = mockStatic(DateTimeUtil.class)) {
            dateTime.when(DateTimeUtil::getSysDate).thenReturn(now);
            assertSame(user, service.findApiUsableById(1L).orElseThrow());
        }

        ArgumentCaptor<UserExample> captor = ArgumentCaptor.forClass(UserExample.class);
        verify(mapper).selectByExample(captor.capture());
        verify(mapper, never()).selectByPrimaryKey(any());

        Configuration configuration = new Configuration();
        configuration.addMapper(UserMapper.class);
        BoundSql boundSql = configuration
                .getMappedStatement(UserMapper.class.getName() + ".selectByExample")
                .getBoundSql(captor.getValue());
        String sql = boundSql.getSql().replaceAll("\\s+", " ").trim();
        List<Object> values = boundSql.getParameterMappings().stream()
                .map(ParameterMapping::getValue)
                .toList();

        assertAll(
                () -> assertTrue(sql.endsWith("WHERE ( SEQ_USER_ID = ? and DELETE_FLAG = ? and PASSWORD_EXPIRE >= ? )"), sql),
                () -> assertEquals(List.of(1L, false, now.toLocalDate()), values));
    }
}
