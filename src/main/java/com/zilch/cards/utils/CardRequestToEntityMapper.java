package com.zilch.cards.utils;

import com.zilch.cards.entity.Card;
import com.zilch.cards.model.ZilchCardDetails;
import com.zilch.cards.model.ZilchCardDetailsResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * this class is used to map the one entity to another using ModelMapper
 */
public class CardRequestToEntityMapper {

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  public static final Function<ZilchCardDetails, Card> mapRequestToCardEntity =
      zilchCardDetails -> {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var cardEntity = modelMapper.map(zilchCardDetails, Card.class);
        java.sql.Date expiryDateCard = null;
        try {
          Date parsedDate = sdf.parse(zilchCardDetails.getExpiryDate().toString());
          expiryDateCard = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
          e.printStackTrace();
          expiryDateCard = (java.sql.Date) sdf.getCalendar().getTime();
        }
        cardEntity.setExpiryDate(expiryDateCard);
        return cardEntity;
      };

  public static final Function<Card, ZilchCardDetailsResponse> mapEntityToCardResponse =
      zilchCardDetails -> {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var cardDetailsResponse = modelMapper.map(zilchCardDetails, ZilchCardDetailsResponse.class);
        cardDetailsResponse.setLast4Digit(
            ZilchCardUtil.getLast4DigitOfCardNumber(zilchCardDetails.getCardNo()));
        return cardDetailsResponse;
      };

}
