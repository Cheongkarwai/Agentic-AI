package com.cheong.agenticai;

import org.springframework.data.domain.Persistable;

import java.util.Objects;
import java.util.UUID;

public interface BaseEntity extends Persistable<String> {

    String getId();
    void setId(String id);

    @Override
    default boolean isNew() {
        boolean result = Objects.isNull(getId());
        if (result) {
            setId(UUID.randomUUID().toString());
        }
        return result;
    }
}
