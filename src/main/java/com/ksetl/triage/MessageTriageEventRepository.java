package com.ksetl.triage;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageTriageEventRepository implements PanacheRepositoryBase<MessageTriageEventEntity, Long> {

}