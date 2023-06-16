package com.ksetl.triage;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MessageTriageEventMapper {

    List<MessageTriageEvent> toDomainList(List<MessageTriageEventEntity> entities);

    MessageTriageEvent toDomain(MessageTriageEventEntity entity);

    @InheritInverseConfiguration(name = "toDomain")
    MessageTriageEventEntity toEntity(MessageTriageEvent domain);

    void updateEntityFromDomain(MessageTriageEvent domain, @MappingTarget MessageTriageEventEntity entity);

    void updateDomainFromEntity(MessageTriageEventEntity entity, @MappingTarget MessageTriageEvent domain);

}