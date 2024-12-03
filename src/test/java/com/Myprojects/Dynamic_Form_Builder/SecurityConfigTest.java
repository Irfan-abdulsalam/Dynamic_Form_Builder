package com.Myprojects.Dynamic_Form_Builder;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAuthenticatedAccessToOtherEndpoints() throws Exception {
        mockMvc.perform(get("/api/forms"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testCsrfIsDisabled() throws Exception {
        mockMvc.perform(get("/api/forms"))
                .andExpect(status().isUnauthorized());
    }
}
