package com.example;

import com.example.exceptions.AccountNotFoundException;
import com.example.model.Account;
import com.example.repositories.AccountRepository;
import com.example.services.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransferServiceModelTest {
    @InjectMocks
    TransferService service;
    @Mock
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

        given(repository.findById(1L)).willReturn(Optional.of(sender)) ;
        given(repository.findById(2L)).willReturn(Optional.of(ressiver)) ;

        //Блок действия
        service.transferMoney(1,2,new BigDecimal(500));


        //Блок проверки
        verify(repository).changeAmount(1,new BigDecimal(500));
        verify(repository).changeAmount(2,new BigDecimal(2500));
    }
    @Test
    public void moneyTransferAccountNotFoundNotTest(){
        //Блок предусловия
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));



        given(repository.findById(1L)).willReturn(Optional.of(sender)) ;
        given(repository.findById(2L)).willReturn(Optional.empty()) ;

        //Блок действия
       // service.transferMoney(1,2,new BigDecimal(500));
        assertThrows(AccountNotFoundException.class,()->{service.transferMoney(1,2,new BigDecimal(500));});


        //Блок проверки
       // verify(repository).changeAmount(1,new BigDecimal(500));
        verify(repository,never()).changeAmount(anyLong(),any());


    }
}
