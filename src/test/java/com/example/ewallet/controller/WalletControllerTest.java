package com.example.ewallet.controller;

import com.example.ewallet.dto.BalanceSummaryDTO;
import com.example.ewallet.services.AccountService;
import com.example.ewallet.services.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
@AutoConfigureMockMvc
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private AccountService accountService;

    @Test
    void getBalanceSummary_returnsSummary() throws Exception {
        BalanceSummaryDTO summary = new BalanceSummaryDTO(1L, 100.0);
        when(walletService.getBalanceSummary(1L)).thenReturn(summary);

        mockMvc.perform(get("/api/wallets/balance-summary/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.balance").value(100.0));
    }
}

