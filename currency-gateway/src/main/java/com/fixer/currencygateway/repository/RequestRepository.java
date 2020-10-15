package com.fixer.currencygateway.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixer.currencygateway.model.Request;

public interface RequestRepository extends JpaRepository<Request, UUID> {

}
