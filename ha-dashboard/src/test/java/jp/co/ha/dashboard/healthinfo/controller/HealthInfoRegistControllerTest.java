package jp.co.ha.dashboard.healthinfo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoService;
import jp.co.ha.db.entity.HealthInfo;

class HealthInfoRegistControllerTest {

    private static final Long USER_ID = 1L;
    private static final HealthInfoDto INPUT = new HealthInfoDto(
            new BigDecimal("170.0"), new BigDecimal("65.0"));

    private HealthInfoRegistController controller;
    private HealthInfoSearchService searchService;
    private HealthInfoService healthInfoService;
    private MockHttpServletRequest request;
    private ExtendedModelMap model;
    private HealthInfoForm sessionForm;
    private HealthInfoRegistApiResponse response;

    @BeforeEach
    void setUp() throws BaseException {
        searchService = mock(HealthInfoSearchService.class);
        healthInfoService = mock(HealthInfoService.class);
        controller = new HealthInfoRegistController();
        ReflectionTestUtils.setField(controller, "healthInfoSearchService", searchService);
        ReflectionTestUtils.setField(controller, "healthInfoService", healthInfoService);
        ReflectionTestUtils.setField(controller, "sessionComponent", new SessionComponent());

        request = new MockHttpServletRequest();
        model = new ExtendedModelMap();
        sessionForm = new HealthInfoForm();
        sessionForm.setHeight(INPUT.height());
        sessionForm.setWeight(INPUT.weight());
        request.getSession().setAttribute("seqUserId", USER_ID);
        request.getSession().setAttribute("healthInfoForm", sessionForm);

        HealthInfoRegistApiResponse.HealthInfo registered = new HealthInfoRegistApiResponse.HealthInfo();
        registered.setSeqHealthInfoId(101L);
        registered.setHeight(INPUT.height());
        registered.setWeight(INPUT.weight());
        registered.setBmi(new BigDecimal("22.5"));
        registered.setStandardWeight(new BigDecimal("63.6"));
        response = new HealthInfoRegistApiResponse();
        response.setHealthInfo(registered);
        when(healthInfoService.regist(INPUT, USER_ID)).thenReturn(response);
    }

    @Test
    void completesWithoutComparisonWhenThereIsNoUndeletedPreviousHealthInfo()
            throws BaseException {
        // 削除済みの健康情報しかない場合も、未削除の前回情報は取得されない。
        when(searchService.findLatestBySeqUserId(USER_ID)).thenReturn(Optional.empty());

        String view = controller.complete(model, new HealthInfoForm(), request);

        assertAll(
                () -> assertEquals("healthinfo/complete", view),
                () -> assertEquals(true, model.get("isFirstReg")),
                () -> assertSame(sessionForm, model.get("healthInfo")),
                () -> assertEquals(101L, sessionForm.getSeqHealthInfoId()),
                () -> assertEquals(new BigDecimal("22.5"), sessionForm.getBmi()),
                () -> assertSame(sessionForm, request.getSession().getAttribute("healthInfoForm")));
        verify(searchService).findLatestBySeqUserId(USER_ID);
        verifyNoMoreInteractions(searchService);
        verify(healthInfoService, never()).addModel(any(), any(), any());
        verify(healthInfoService).regist(INPUT, USER_ID);
        verify(healthInfoService).sendHealthInfoMail(response);
    }

    @Test
    void comparesWithUndeletedPreviousHealthInfoBeforeRegistering() throws BaseException {
        HealthInfo previous = new HealthInfo();
        previous.setSeqHealthInfoId(77L);
        previous.setSeqUserId(USER_ID);
        previous.setWeight(new BigDecimal("67.0"));
        previous.setDeleteFlag(false);
        when(searchService.findLatestBySeqUserId(USER_ID)).thenReturn(Optional.of(previous));

        String view = controller.complete(model, new HealthInfoForm(), request);

        assertAll(
                () -> assertEquals("healthinfo/complete", view),
                () -> assertEquals(false, model.get("isFirstReg")),
                () -> assertSame(sessionForm, model.get("healthInfo")),
                () -> assertEquals(101L, sessionForm.getSeqHealthInfoId()));
        InOrder order = inOrder(searchService, healthInfoService);
        order.verify(searchService).findLatestBySeqUserId(USER_ID);
        order.verify(healthInfoService).addModel(same(model), eq(INPUT), same(previous));
        order.verify(healthInfoService).regist(INPUT, USER_ID);
        order.verify(healthInfoService).sendHealthInfoMail(response);
        verifyNoMoreInteractions(searchService);
    }
}
