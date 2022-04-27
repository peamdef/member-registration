package com.test.registation.service;

import com.test.registation.ApplicationMockitoTest;
import com.test.registation.entity.User;
import com.test.registation.entity.Wallet;
import com.test.registation.model.CommonResponse;
import com.test.registation.model.TopUpParam;
import com.test.registation.repository.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.test.registation.constant.StatusCode.S0000;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceMockitoTest extends ApplicationMockitoTest {
    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;
    @Test
    public void topUpAmountInWallet(){
        TopUpParam param = new TopUpParam();
        param.setAmount(1000);
        param.setReferenceCode(referenceCode);

        Wallet wallet = new Wallet(referenceCode,0,0);
        when(walletRepository.findByReferenceCode(any(String.class))).thenReturn(Optional.of(wallet));

        when(walletRepository.save(any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);

        CommonResponse<Wallet> actual = walletService.topUpAmountInWallet(param);
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals(referenceCode,actual.getData().getReferenceCode());
        assertEquals(Optional.of(1000).get(),actual.getData().getAmount());
        assertEquals(Optional.of(0).get(),actual.getData().getPoint());
    }

    @Test(expected = Exception.class)
    public void topUpAmountInWalletWithWLTE1001(){
        TopUpParam param = new TopUpParam();
        param.setAmount(1000);
        param.setReferenceCode(referenceCode);
        when(walletRepository.findByReferenceCode(any(String.class))).thenReturn(Optional.empty());
        walletService.topUpAmountInWallet(param);
    }

    @Test
    public void getWalletByReferenceCode(){
        Wallet wallet = new Wallet(referenceCode,0,0);
        when(walletRepository.findByReferenceCode(any(String.class))).thenReturn(Optional.of(wallet));
        CommonResponse<Wallet> actual = walletService.getWalletByReferenceCode(referenceCode);
        assertEquals(S0000.getCode(),actual.getCode());
        assertEquals(S0000.getMessage(),actual.getMessage());
        assertEquals(referenceCode,actual.getData().getReferenceCode());
        assertEquals(Optional.of(0).get(),actual.getData().getAmount());
        assertEquals(Optional.of(0).get(),actual.getData().getPoint());
    }
    @Test(expected = Exception.class)
    public void getWalletByReferenceCodeWithWLTE1001(){
        Wallet wallet = new Wallet(referenceCode,0,0);
        when(walletRepository.findByReferenceCode(any(String.class))).thenReturn(Optional.empty());
        walletService.getWalletByReferenceCode(referenceCode);
    }
}
