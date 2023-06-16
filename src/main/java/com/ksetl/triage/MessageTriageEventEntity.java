package com.ksetl.triage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "MessageTriageEvent")
@Table(name = "message_triage_event")
@Data
public class MessageTriageEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_triage_event_id")
    private Long messageTriageEventId;

    @Column(name = "bootstrap_servers")
    @NotEmpty
    private String bootstrapServers;

    @Column(name = "topic")
    @NotEmpty
    private String topic;

    @Column(name = "partition")
    @NotNull
    private Integer partition;

    @Column(name = "offset_value")
    @NotNull
    private Long offset;

    @Column(name = "error_message")
    @NotEmpty
    private String errorMessage;

}