package com.zilch.cards.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Card {

  @Id
  @GeneratedValue
  private int id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String userFirstName;

  @Column(nullable = false)
  private String userLastName;

  @Size(min = 16, max = 16)
  @Column(unique = true)
  private String cardNo;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date expiryDate;

  @Column(nullable = false)
  private String cvv;

}
