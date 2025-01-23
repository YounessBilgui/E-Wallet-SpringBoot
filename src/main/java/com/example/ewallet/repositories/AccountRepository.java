package com.example.ewallet.repositories;


import com.example.ewallet.dto.AccountSummaryDTO;
import com.example.ewallet.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhone(String phone);
    @Query("SELECT new com.example.ewallet.dto.AccountSummaryDTO(a.email, a.name, a.phone, a.wallet.id, a.wallet.balance, :transaction) " +
            "FROM Account a " +
            "WHERE a.id = :accountId")
    AccountSummaryDTO getAccountSummary(@Param("accountId") Long accountId, @Param("transaction") List<Map<String, Object>> transaction);
}
