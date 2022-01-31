package com.zilch.cards.service;

import com.zilch.cards.entity.Card;
import com.zilch.cards.model.ZilchCardDetails;
import com.zilch.cards.model.ZilchCardDetailsResponse;
import com.zilch.cards.model.ZilchCardResponse;
import com.zilch.cards.repository.CardRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZilchCardServiceTest {

  @Mock
  private CardNumberValidatorService cardNumberValidatorService;

  @Mock
  private CardRepository cardRepository;

  @InjectMocks
  private ZilchCardService testClass;


  @Test
  public void testAddCardDetailsSuccessCase() {

    //given
    ZilchCardDetails zilchCardDetails = ZilchCardDetails();
    //when
    Mockito.when(cardNumberValidatorService.validateCreditCardNumber(Mockito.anyString()))
        .thenReturn(true);
    Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(getCardEntity());

    ZilchCardResponse getCardEntity = testClass.addCardDetails(zilchCardDetails);
    //then
    Assertions.assertEquals(getCardEntity.getLast4Digit(), "9113");

  }

  @Test
  public void testGetCardDetailsSuccessCase() {

    //given
    String userId = "A123";
    String last4Digit = "9113";
    //when
    Mockito.when(
            cardRepository.findByUserIdAndCardNoEndingWith(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(getCardEntity());

    ZilchCardDetailsResponse cardDetails = testClass.getCardDetails(userId, last4Digit);
    //then
    Assertions.assertEquals(cardDetails.getLast4Digit(), "9113");

  }

  private ZilchCardDetails ZilchCardDetails() {
    ZilchCardDetails zilchCardDetails = new ZilchCardDetails();
    zilchCardDetails.setUserId("A123");
    zilchCardDetails.setUserFirstName("firstName");
    zilchCardDetails.setUserLastName("lastName");
    zilchCardDetails.setExpiryDate(LocalDate.of(2024, 2, 2));
    zilchCardDetails.setCardNo("4417123456789113");
    zilchCardDetails.setCvv(123);

    return zilchCardDetails;
  }

  private Card getCardEntity() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    String dateInString = "2024-02-02";
    Date date = null;
    try {
      date = formatter.parse(dateInString);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Card cardEntity = new Card();
    cardEntity.setUserId("A123");
    cardEntity.setUserFirstName("firstName");
    cardEntity.setUserLastName("lastName");
    cardEntity.setExpiryDate(date);
    cardEntity.setCardNo("4417123456789113");
    cardEntity.setCvv("123");

    return cardEntity;
  }


}
