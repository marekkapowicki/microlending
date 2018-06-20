package com.marekk.microlending.domain.application.validation;

import java.util.Optional;

interface IpValidationRepository {

    ApplicationsByIp save(ApplicationsByIp applicationsByIp);

    Optional<ApplicationsByIp> find(ApplicationsByIp applicationsByIp);

    ApplicationsByIp remove(ApplicationsByIp applicationsByIp);

    boolean removeAll();
}
