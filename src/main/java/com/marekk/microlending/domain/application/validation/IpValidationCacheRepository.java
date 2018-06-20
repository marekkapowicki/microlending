package com.marekk.microlending.domain.application.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@CacheConfig(cacheNames = {"ip"})
class IpValidationCacheRepository implements IpValidationRepository {

    @Override
    @CachePut(key = "#applicationsByIp.inetAddress")
    public ApplicationsByIp save(ApplicationsByIp applicationsByIp) {
        log.debug("putting {} to cache", applicationsByIp);
        return applicationsByIp;

    }

    @Override
    @Cacheable(key = "#applicationsByIp.inetAddress")
    public Optional<ApplicationsByIp> find(ApplicationsByIp applicationsByIp) {
        log.debug("finding {} in cache", applicationsByIp);
        return Optional.ofNullable(applicationsByIp);
    }

    @Override
    @CacheEvict(key = "#applicationsByIp.inetAddress")
    public ApplicationsByIp remove(ApplicationsByIp applicationsByIp) {
        log.debug("removing {} from cache", applicationsByIp);
        return applicationsByIp;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeAll() {
        return true;
    }

}
