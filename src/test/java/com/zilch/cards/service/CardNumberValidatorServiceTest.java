package com.zilch.cards.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CardNumberValidatorServiceTest {

  @InjectMocks
  CardNumberValidatorService cardNumberValidatorService;

  @Test
  public void testValidateCreditCardNumberWithValidCard() {
    //given
    String cardNumber = "4417123456789113";
    //when
    boolean isValidCard = cardNumberValidatorService.validateCreditCardNumber(cardNumber);
    //then
    Assertions.assertTrue(isValidCard);
  }

  @Test
  public void testValidateCreditCardNumberWithInValidCard() {
    //given
    String cardNumber = "4417123456789119";
    //when
    boolean isValidCard = cardNumberValidatorService.validateCreditCardNumber(cardNumber);
    //then
    Assertions.assertFalse(isValidCard);
  }

}
