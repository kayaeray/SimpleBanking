package com.eteration.simplebanking;

import com.eteration.simplebanking.model.entity.AccountEntity;
import com.eteration.simplebanking.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;
    @Autowired
    public DemoApplication(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) {
        accountRepository.save(AccountEntity.builder()
				.accountNumber("669-7788")
				.balance(BigDecimal.ZERO)
				.owner("Eray Kaya")
				.createdDate(LocalDateTime.now())
				.transactions(new ArrayList<>()).build());
    }

}
