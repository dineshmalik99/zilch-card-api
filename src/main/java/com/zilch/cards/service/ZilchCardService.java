package com.zilch.cards.service;

import com.zilch.cards.exception.BaseException;
import com.zilch.cards.exception.ErrorCode;
import com.zilch.cards.model.ZilchCardDetails;
import com.zilch.cards.model.ZilchCardDetailsResponse;
import com.zilch.cards.model.ZilchCardResponse;
import com.zilch.cards.repository.CardRepository;
import com.zilch.cards.utils.CardRequestToEntityMapper;
import com.zilch.cards.utils.ZilchCardUtil;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ZilchCardService is the first service class interacting with the controller and doing business
 * logic
 */
@Service
@Flogger
public class ZilchCardService {

  private CardNumberValidatorService cardNumberValidatorService;

  private CardRepository cardRepository;

  public ZilchCardService(@Autowired
      CardNumberValidatorService cardNumberValidatorService,
      @Autowired CardRepository cardRepository) {
    this.cardNumberValidatorService = cardNumberValidatorService;
    this.cardRepository = cardRepository;
  }

  /**
   * this method validate the card no received in request and then save the entity in DB after
   * successful insert will send the last4Digit of the save entity
   *
   * @param zilchCardDetails
   * @return ZilchCardResponse
   */
  public ZilchCardResponse addCardDetails(ZilchCardDetails zilchCardDetails) {
    boolean isValidCardNo = cardNumberValidatorService.validateCreditCardNumber(
        zilchCardDetails.getCardNo());
    if (!isValidCardNo) {
      var exception = new BaseException(ErrorCode.INVALID_REQUEST_BODY.getApiError(
          "Invalid CardNo :" + zilchCardDetails.getCardNo()));
      log.atSevere().log("Invalid card no %s in request for user %s", zilchCardDetails.getCardNo(),
          zilchCardDetails.getUserId());
      throw exception;
    }
    var entityToSave = CardRequestToEntityMapper.mapRequestToCardEntity.apply(zilchCardDetails);
    var savedEntity = cardRepository.save(entityToSave);
    var zilchCardResponse = new ZilchCardResponse();
    zilchCardResponse.setLast4Digit(
        ZilchCardUtil.getLast4DigitOfCardNumber(savedEntity.getCardNo()));
    log.atInfo().log("Card Details saved in db for user, %s", savedEntity.getUserId());
    return zilchCardResponse;
  }

  /**
   * this method check for card details in DB and if found will return ZilchCardDetailsResponse if
   * not found then throw BaseException
   *
   * @param userId
   * @param last4Digit
   * @return ZilchCardDetailsResponse
   */
  public ZilchCardDetailsResponse getCardDetails(String userId, String last4Digit) {
    var savedMatchingRecords = cardRepository.findByUserIdAndCardNoEndingWith(userId, last4Digit);
    if (savedMatchingRecords == null) {
      var exception = new BaseException(ErrorCode.NOT_FOUND.getApiError(
          userId + ", and last4Digit :" + last4Digit));
      log.atSevere().log("No record found for user %s, last4Digit %s", userId, last4Digit);
      throw exception;
    }
    var cardDetailsResponse = CardRequestToEntityMapper.mapEntityToCardResponse.apply(
        savedMatchingRecords);
    log.atInfo().log("Result returned for user %s, last4Digit %s", userId, last4Digit);
    return cardDetailsResponse;
  }


}
