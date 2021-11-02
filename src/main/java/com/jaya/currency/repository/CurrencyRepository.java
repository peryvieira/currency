package com.jaya.currency.repository;

import com.jaya.currency.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

    Optional<Currency> findByAbbreviation(String abbreviation);
}
