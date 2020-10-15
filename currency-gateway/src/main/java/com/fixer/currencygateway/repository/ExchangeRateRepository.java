package com.fixer.currencygateway.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fixer.currencygateway.model.Currency;
import com.fixer.currencygateway.model.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    Optional<ExchangeRate> findFirstByCurrencyOrderByTimestampDesc(Currency currency);
    List<ExchangeRate> findAllByCurrencyAndTimestampBetween(Currency currency, LocalDateTime date, LocalDateTime after);

}
