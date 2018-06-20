package com.marekk.microlending.domain.application.validation;


import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.marekk.microlending.domain.application.validation.ApplicationsByIp.firstApplication;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
public class IpValidationService {
    IpValidationRepository ipValidationRepository;

    @Transactional(propagation = REQUIRES_NEW)
    public ApplicationsByIp rememberIpHit(InetAddress inetAddress) {
        Optional<ApplicationsByIp> inCache = ipValidationRepository.find(firstApplication(inetAddress));
        ApplicationsByIp toSave = inCache
                .map(it -> {
                    ipValidationRepository.remove(it);
                    return it.withIncrementCounter();
                })
                .orElse(firstApplication(inetAddress));
        return ipValidationRepository.save(toSave);
    }

    @Transactional(readOnly = true)
    public int getHitCount(InetAddress inetAddress) {
        return ipValidationRepository.find(firstApplication(inetAddress))
                .map(ApplicationsByIp::getCounter)
                .map(AtomicInteger::intValue)
                .orElse(0);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void flushIpsDaily() {
        log.info("Flushing client IPs");
        ipValidationRepository.removeAll();
    }


}
