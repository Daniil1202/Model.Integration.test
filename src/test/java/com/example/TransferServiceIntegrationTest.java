package com.example;

import com.example.model.Account;
import com.example.repositories.AccountRepository;
import com.example.services.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransferServiceIntegrationTest {
    @Autowired
    TransferService service;
    @MockBean
    AccountRepository repository;

    @Test
    public void moneyTransferCorrectFlow(){
        //Блок предусловия
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));


        Account ressiver = new Account();
        ressiver.setId(2);
        ressiver.setAmount(new BigDecimal(2000));

        when(repository.findById(1L)).thenReturn(Optional.of(sender)) ;
        when(repository.findById(2L)).thenReturn(Optional.of(ressiver)) ;

        //Блок действия
        service.transferMoney(1,2,new BigDecimal(500));


        //Блок проверки
        verify(repository).changeAmount(1,new BigDecimal(500));
        verify(repository).changeAmount(2,new BigDecimal(2500));
    }
}
