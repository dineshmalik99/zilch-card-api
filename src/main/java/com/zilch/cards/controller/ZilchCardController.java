package com.zilch.cards.controller;

import com.zilch.cards.api.ZilchApiDelegate;
import com.zilch.cards.model.ZilchCardDetails;
import com.zilch.cards.model.ZilchCardDetailsResponse;
import com.zilch.cards.model.ZilchCardResponse;
import com.zilch.cards.service.ZilchCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ZilchCardController implements ZilchApiDelegate {

  private ZilchCardService zilchCardService;

  public ZilchCardController(@Autowired ZilchCardService zilchCardService) {
    this.zilchCardService = zilchCardService;
  }


  @Override
  public ResponseEntity<ZilchCardResponse> addCardDetails(ZilchCardDetails zilchCardDetails) {
    var zilchCardResponse = zilchCardService.addCardDetails(zilchCardDetails);
    return ResponseEntity.ok(zilchCardResponse);
  }


  @Override
  public ResponseEntity<ZilchCardDetailsResponse> getCardDetails(String userId, String last4Digit) {
    var cardDetailsResponse = zilchCardService.getCardDetails(userId, last4Digit);
    return ResponseEntity.ok(cardDetailsResponse);
  }
}
