package com.example.ewallet.repositories;


import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByAccountId(Long accountId);
    //    @Query("SELECT w FROM Wallet w JOIN w.account a WHERE a.id = :accountId")
    //    Wallet findWalletByAccountId(@Param("accountId")Long accountId);
}
