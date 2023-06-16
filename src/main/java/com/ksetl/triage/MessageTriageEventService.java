package com.ksetl.triage;

import com.ksetl.ServiceException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class MessageTriageEventService {

    private final MessageTriageEventRepository repository;
    private final MessageTriageEventMapper mapper;

    public List<MessageTriageEvent> findAll() {
        return this.mapper.toDomainList(repository.findAll().list());
    }

    public Optional<MessageTriageEvent> findById(@NonNull Long messageTriageEventId) {
        return repository.findByIdOptional(messageTriageEventId)
                .map(mapper::toDomain);
    }

    @Transactional
    public void save(@Valid MessageTriageEvent messageTriageEvent) {
        log.debug("Saving MessageTriageEvent: {}", messageTriageEvent);
        MessageTriageEventEntity entity = mapper.toEntity(messageTriageEvent);
        repository.persist(entity);
        mapper.updateDomainFromEntity(entity, messageTriageEvent);
    }

    @Transactional
    public void update(@Valid MessageTriageEvent messageTriageEvent) {
        log.debug("Updating MessageTriageEvent: {}", messageTriageEvent);
        if (Objects.isNull(messageTriageEvent.getMessageTriageEventId())) {
            throw new ServiceException("MessageTriageEvent does not have a messageTriageEventId");
        }
        MessageTriageEventEntity entity = repository.findByIdOptional(messageTriageEvent.getMessageTriageEventId())
                .orElseThrow(() -> new ServiceException("No MessageTriageEvent found for messageTriageEventId[%s]", messageTriageEvent.getMessageTriageEventId()));
        mapper.updateEntityFromDomain(messageTriageEvent, entity);
        repository.persist(entity);
        mapper.updateDomainFromEntity(entity, messageTriageEvent);
    }

}