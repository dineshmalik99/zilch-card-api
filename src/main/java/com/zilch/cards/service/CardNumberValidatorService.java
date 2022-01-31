package com.zilch.cards.service;

import com.zilch.cards.exception.BaseException;
import com.zilch.cards.exception.ErrorCode;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Service;

/**
 * CardNumberValidatorService is used to validate the card no received in request
 */
@Service
@Flogger
public class CardNumberValidatorService {

  /**
   * implemented using Luhn Algorithm #ref https://en.wikipedia.org/wiki/Luhn_algorithm if no is not
   * in Luhn format will return false
   */
  public boolean validateCreditCardNumber(String cardNoString) {

    int[] ints = new int[cardNoString.length()];
    for (int i = 0; i < cardNoString.length(); i++) {
      try {
        ints[i] = Integer.parseInt(cardNoString.substring(i, i + 1));
      } catch (NumberFormatException e) {
        var exception = new BaseException(ErrorCode.INVALID_REQUEST_BODY.getApiError(
            "Invalid CardNo :" + cardNoString));
        log.atSevere().log("Invalid card no %s, not in number format", cardNoString);
        throw exception;
      }
    }
    for (int i = ints.length - 2; i >= 0; i = i - 2) {
      int j = ints[i];
      j = j * 2;
      if (j > 9) {
        j = j % 10 + 1;
      }
      ints[i] = j;
    }
    int sum = 0;
    for (int i = 0; i < ints.length; i++) {
      sum += ints[i];
    }
    if (sum % 10 == 0) {
      return true;
    } else {
      return false;
    }
  }


}
