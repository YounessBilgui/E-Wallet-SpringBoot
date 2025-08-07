package com.example.ewallet.services;

import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.AccountRepository;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Wallet fromWallet;
    private Wallet toWallet;

    @BeforeEach
    void setUp() {
        fromWallet = new Wallet();
        fromWallet.setId(1L);
        fromWallet.setBalance(100.0);

        toWallet = new Wallet();
        toWallet.setId(2L);
        toWallet.setBalance(50.0);
    }

    @Test
    void transferFundsCreatesDebitAndCreditTransactions() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(toWallet));

        TransferDTO dto = new TransferDTO();
        dto.setFromWallet(1L);
        dto.setToWallet(2L);
        dto.setAmount(30.0);

        transactionService.transferFunds(dto);

        assertEquals(70.0, fromWallet.getBalance());
        assertEquals(80.0, toWallet.getBalance());

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(2)).save(captor.capture());
        List<Transaction> savedTransactions = captor.getAllValues();

        Transaction debit = savedTransactions.get(0);
        assertEquals(Transaction.TransactionType.DEBIT, debit.getType());
        assertEquals(Transaction.TransactionStatus.SUCCESS, debit.getStatus());
        assertEquals(fromWallet, debit.getWallet());

        Transaction credit = savedTransactions.get(1);
        assertEquals(Transaction.TransactionType.CREDIT, credit.getType());
        assertEquals(Transaction.TransactionStatus.SUCCESS, credit.getStatus());
        assertEquals(toWallet, credit.getWallet());
    }
}
