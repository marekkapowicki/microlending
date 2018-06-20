package com.marekk.microlending.domain.loan.command.extension;

import lombok.experimental.FieldDefaults;

import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
class InMemoryExtensionCreatorRepository implements ExtensionCreatorRepository {

    final ConcurrentHashMap<String, ExtensionEntity> map = new ConcurrentHashMap<>();

    @Override
    public ExtensionEntity save(ExtensionEntity extensionEntity) {
        return map.putIfAbsent(extensionEntity.getUuid(), extensionEntity);
    }
}
