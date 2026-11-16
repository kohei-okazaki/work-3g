package jp.co.ha.business.api.healthinfoapp.service;

import static jp.co.ha.business.exception.ApiErrorCode.API_EXEC_ERROR;
import static jp.co.ha.business.exception.DashboardErrorCode.ACCOUNT_ILLEGAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.business.api.healthinfoapp.request.BaseAppApiRequest;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoReferenceApiRequest;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.service.impl.HealthInfoReferenceServiceImpl;
import jp.co.ha.business.api.healthinfoapp.service.impl.HealthInfoRegistServiceImpl;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.User;

/**
 * 登録・照会の双方がAPI利用可能ユーザの検索結果を使って利用判定することを確認する。
 */
class CommonServiceTest {

    private static final String API_KEY = "0123456789abcdef".repeat(4);

    @ParameterizedTest
    @EnumSource(ApiType.class)
    void rejectsUsersExcludedByTheApiEligibilitySearch(ApiType apiType) {
        UserSearchService search = mock(UserSearchService.class);
        when(search.findApiUsableById(1L)).thenReturn(Optional.empty());
        CommonService service = service(apiType, search);

        BusinessException error = assertThrows(BusinessException.class,
                () -> checkRequest(apiType, service, API_KEY));

        assertEquals(ACCOUNT_ILLEGAL, error.getErrorCode());
        assertEquals("ユーザ情報が存在しません seqUserId:1", error.getDetail());
        verify(search).findApiUsableById(1L);
        verify(search, never()).findById(any());
    }

    @ParameterizedTest
    @EnumSource(ApiType.class)
    void acceptsMatchingApiKeyForEligibleUser(ApiType apiType) {
        UserSearchService search = searchWithEligibleUser();

        assertDoesNotThrow(() -> checkRequest(apiType, service(apiType, search), API_KEY));

        verify(search).findApiUsableById(1L);
        verify(search, never()).findById(any());
    }

    @ParameterizedTest
    @EnumSource(ApiType.class)
    void rejectsMismatchedApiKeyForEligibleUser(ApiType apiType) {
        UserSearchService search = searchWithEligibleUser();

        ApiException error = assertThrows(ApiException.class,
                () -> checkRequest(apiType, service(apiType, search), "f".repeat(64)));

        assertEquals(API_EXEC_ERROR, error.getErrorCode());
    }

    private UserSearchService searchWithEligibleUser() {
        UserSearchService search = mock(UserSearchService.class);
        User user = new User();
        user.setSeqUserId(1L);
        user.setApiKey(API_KEY);
        when(search.findApiUsableById(1L)).thenReturn(Optional.of(user));
        return search;
    }

    private CommonService service(ApiType apiType, UserSearchService search) {
        UserComponent component = new UserComponent();
        ReflectionTestUtils.setField(component, "userSearchService", search);
        CommonService service = apiType == ApiType.REGIST
                ? new HealthInfoRegistServiceImpl() : new HealthInfoReferenceServiceImpl();
        ReflectionTestUtils.setField(service, "userComponent", component);
        return service;
    }

    private void checkRequest(ApiType apiType, CommonService service, String apiKey)
            throws BaseException {
        BaseAppApiRequest request = apiType == ApiType.REGIST
                ? new HealthInfoRegistApiRequest() : new HealthInfoReferenceApiRequest();
        request.setSeqUserId(1L);
        request.setApiKey(apiKey);
        if (apiType == ApiType.REGIST) {
            ((HealthInfoRegistServiceImpl) service)
                    .checkRequest((HealthInfoRegistApiRequest) request);
        } else {
            ((HealthInfoReferenceServiceImpl) service)
                    .checkRequest((HealthInfoReferenceApiRequest) request);
        }
    }

    private enum ApiType {
        REGIST, REFERENCE
    }
}
