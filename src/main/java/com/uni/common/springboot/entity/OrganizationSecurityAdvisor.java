package com.uni.common.springboot.entity;

import com.uni.common.springboot.core.Context;
import com.uni.common.springboot.core.UniRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.MessageFormat;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class OrganizationSecurityAdvisor {
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    // all query methods in JpaRepository and QuerydslPredicateExecutor
    @Pointcut("execution(public * find*(..)) || execution(public * exists*(..)) || " +
            "execution(public * count*(..)) || execution(public * getOne(..))")
    private void queryMethod() {
    }

    // all subclasses of OrganizationAwareRepository
    @Pointcut("within(com.uni.common.springboot.entity.OrganizationAwareRepository+)")
    private void organizationAwareRepository() {
    }

    // all save methods in JpaRepository and QuerydslPredicateExecutor
    @Pointcut("execution(public * save*(..))")
    private void saveMethod() {
    }

    // all delete methods in JpaRepository and QuerydslPredicateExecutor
    @Pointcut("execution(public * delete*(..))")
    private void deleteMethod() {
    }

    @Around("organizationAwareRepository() && queryMethod()")
    public Object queryFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = entityManager.unwrap(Session.class);
        try {
            // Enable the organization filter
            Filter filter = session.enableFilter(OrganizationEntity.ORGANIZATION_FILTER);

            // set organization from context
            String contextOrg = Context.getContext().getOrganizationUid();
            filter.setParameter(OrganizationEntity.ORGANIZATION_UID, contextOrg);

            // Run the query
            Object val = joinPoint.proceed();

            // Run the the results through a second filter in case the first filter above was not used
            if (val instanceof OrganizationEntity) {
                OrganizationEntity entity = (OrganizationEntity) val;
                return entity.getOrganizationUid() == null || entity.getOrganizationUid().equals(contextOrg) ?
                        entity :
                        null;
            }
            else if (val instanceof Optional) {
                Optional<OrganizationEntity> optional = (Optional<OrganizationEntity>) val;
                if (!optional.isPresent()) {
                    return optional;
                }

                OrganizationEntity entity = optional.get();
                return entity.getOrganizationUid() == null || entity.getOrganizationUid().equals(contextOrg) ?
                        optional : Optional.empty();
            }
            return val;
        }
        finally {
            session.disableFilter(OrganizationEntity.ORGANIZATION_FILTER);
        }
    }

    @Around("organizationAwareRepository() && saveMethod()")
    public Object saveSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // only methods with a single argument is supported
        if (args == null || args.length != 1) {
            throw createMethodNotSupportedException(joinPoint);
        }

        Object arg = args[0];
        if (arg instanceof OrganizationEntity) {
            validateAndPopulateOrgBeforeSave((OrganizationEntity) arg);
        }
        else if (arg instanceof Iterable) {
            Iterable iterable = (Iterable) arg;
            for (Object o : iterable) {
                if (o instanceof OrganizationEntity) {
                    validateAndPopulateOrgBeforeSave((OrganizationEntity) o);
                } else {
                    throw createMethodNotSupportedException(joinPoint);
                }
            }
        }
        else {
            throw createMethodNotSupportedException(joinPoint);
        }

        // Proceed to save
        return joinPoint.proceed();
    }

    private void validateAndPopulateOrgBeforeSave(OrganizationEntity entity) {
        Context context = Context.getContext();
        String contextOrg = context.getOrganizationUid();

        if (contextOrg == null) {
            throw new UniRuntimeException("Organization must be set in the Context before saving a record");
        }

        if (entity.getOrganizationUid() == null) {
            entity.setOrganizationUid(contextOrg);
        }

        if (!entity.getOrganizationUid().equals(contextOrg)) {
            throw new UniRuntimeException(
                    MessageFormat.format("Organization mismatch in save. Entity = {0}, entity org = {1}",
                            entity, contextOrg));
        }
    }

    @Around("organizationAwareRepository() && deleteMethod()")
    public Object deleteSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // e.g deleteAll(), deleteAllInBatch()
        if (args == null || args.length != 1) {
            throw createMethodNotSupportedException(joinPoint);
        }

        Object arg = args[0];
        if (arg instanceof OrganizationEntity) {
            validateOrgBeforeDelete((OrganizationEntity) arg);
        }
        else if (arg instanceof Iterable) {
            Iterable iterable = (Iterable) arg;
            for (Object o : iterable) {
                if (o instanceof OrganizationEntity) {
                    validateOrgBeforeDelete((OrganizationEntity) o);
                } else {
                    throw createMethodNotSupportedException(joinPoint);
                }
            }
        }
        else // e.g. deleteById()
        {
            throw createMethodNotSupportedException(joinPoint);
        }

        // Proceed to delete
        return joinPoint.proceed();
    }

    private void validateOrgBeforeDelete(OrganizationEntity entity) {
        Context context = Context.getContext();
        String contextOrg = context.getOrganizationUid();

        if (entity.getOrganizationUid() != null && !entity.getOrganizationUid().equals(contextOrg)) {
            throw new UniRuntimeException(
                    MessageFormat.format("Organization mismatch in delete. Entity = {0}, entity org = {1}",
                            entity, contextOrg));
        }
    }

    private UniRuntimeException createMethodNotSupportedException(ProceedingJoinPoint joinPoint) {
        return new UniRuntimeException(
                MessageFormat.format("Method {0} is not supported in organization aware entity.",
                        joinPoint.getSignature().getName()));
    }
}
