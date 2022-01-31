package com.zilch.cards.utils;

public class ZilchCardUtil {


  public static String getLast4DigitOfCardNumber(String cardNo){
    return cardNo.substring(cardNo.length()-4);
  }

}
