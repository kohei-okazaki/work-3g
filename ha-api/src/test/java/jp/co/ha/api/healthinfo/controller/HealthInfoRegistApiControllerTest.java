package jp.co.ha.api.healthinfo.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.healthinfoapp.service.HealthInfoRegistService;
import jp.co.ha.common.validator.BeanValidator;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * 健康情報登録APIのリクエストIFの回帰テスト。
 */
class HealthInfoRegistApiControllerTest {

    private static final String API_KEY = "0123456789abcdef".repeat(4);

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Test
    void acceptsHeightAndWeightWithoutTestMode() throws Exception {
        HealthInfoRegistService service = mock(HealthInfoRegistService.class);
        HealthInfoRegistApiController controller = new HealthInfoRegistApiController();
        ReflectionTestUtils.setField(controller, "service", service);
        ReflectionTestUtils.setField(controller, "validator",
                new BeanValidator<HealthInfoRegistApiRequest>());

        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new JacksonJsonHttpMessageConverter(jsonMapper))
                .build();

        mvc.perform(post("/api/{seq_user_id}/healthinfo", 1L)
                .header("Api-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"height": 170.5, "weight": 65.2}
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isString())
                .andExpect(jsonPath("$.result").value("0"));

        ArgumentCaptor<HealthInfoRegistApiRequest> requestCaptor = ArgumentCaptor
                .forClass(HealthInfoRegistApiRequest.class);
        InOrder serviceCalls = inOrder(service);
        serviceCalls.verify(service).checkRequest(requestCaptor.capture());
        serviceCalls.verify(service).execute(same(requestCaptor.getValue()),
                any(HealthInfoRegistApiResponse.class));

        HealthInfoRegistApiRequest request = requestCaptor.getValue();
        assertAll(
                () -> assertEquals(1L, request.getSeqUserId()),
                () -> assertEquals(API_KEY, request.getApiKey()),
                () -> assertEquals(new BigDecimal("170.5"), request.getHeight()),
                () -> assertEquals(new BigDecimal("65.2"), request.getWeight()));
    }

    @Test
    void omitsTestModeFromSerializedRequest() {
        HealthInfoRegistApiRequest request = new HealthInfoRegistApiRequest();
        request.setHeight(new BigDecimal("170.5"));
        request.setWeight(new BigDecimal("65.2"));

        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(request));

        assertAll(
                () -> assertFalse(json.has("test_mode")),
                () -> assertFalse(json.has("testMode")));
    }
}
