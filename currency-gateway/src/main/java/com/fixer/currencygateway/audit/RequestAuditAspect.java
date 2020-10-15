package com.fixer.currencygateway.audit;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fixer.currencygateway.model.Request;
import com.fixer.currencygateway.model.RequestAudit;
import com.fixer.currencygateway.repository.RequestAuditRepository;

@Component
@Aspect
public class RequestAuditAspect {

    private RequestAuditRepository requestAuditRepository;

    public RequestAuditAspect(RequestAuditRepository requestAuditRepository) {
        this.requestAuditRepository = requestAuditRepository;
    }

    @Pointcut("execution(* com.fixer.currencygateway.service.impl.RequestServiceImpl.createRateRequest(..))")
    public void createRateRequest() {
        // declaring pointcut
    }

    @Pointcut("execution(* com.fixer.currencygateway.service.impl.RequestServiceImpl.createHistoryRateRequest(..))")
    public void createHistoryRateRequest() {
        // declaring pointcut
    }

    @AfterReturning("createRateRequest() && args(request,..)")
    public void auditCreateRequest(JoinPoint joinPoint, Request request) throws InterruptedException {
        saveAuditedRequest(request);
    }

    @AfterReturning("createHistoryRateRequest()  && args(request,..)")
    public void auditHistoryRequest(JoinPoint joinPoint, Request request) throws InterruptedException {
        saveAuditedRequest(request);
    }

    private void saveAuditedRequest(Request request) {
        RequestAudit requestAudit = new RequestAudit();
        requestAudit.setRequest(request);
        requestAudit.setClient(request.getClient());
        requestAudit.setServiceName(request.getServiceName());
        requestAudit.setTimestamp(LocalDateTime.now());
        requestAuditRepository.save(requestAudit);
    }
}
