package com.marekk.microlending.domain.loan.command.extension;

import org.springframework.data.repository.Repository;

interface ExtensionCreatorRepository extends Repository<ExtensionEntity, Long> {
    ExtensionEntity save(ExtensionEntity extensionEntity);
}
