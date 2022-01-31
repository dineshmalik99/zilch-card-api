package com.zilch.cards.repository;

import com.zilch.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

  Card findByUserIdAndCardNoEndingWith(String userId, String last4Digit);

}
