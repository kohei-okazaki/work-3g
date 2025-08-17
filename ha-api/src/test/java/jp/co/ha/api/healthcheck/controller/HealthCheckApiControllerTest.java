package jp.co.ha.api.healthcheck.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * {@linkplain HealthCheckApiController} Test
 * 
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckApiControllerTest {

    /** Mock */
    @Autowired
    private MockMvc mockMvc;

    /**
     * test
     * 
     * @throws Exception
     */
    @Test
    public void testHealthCheckApi() throws Exception {
        mockMvc.perform(get("/api/healthcheck")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
