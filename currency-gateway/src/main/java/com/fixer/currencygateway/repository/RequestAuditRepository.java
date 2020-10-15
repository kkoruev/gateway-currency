package com.fixer.currencygateway.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fixer.currencygateway.model.RequestAudit;

public interface RequestAuditRepository extends JpaRepository<RequestAudit, UUID> {
}
