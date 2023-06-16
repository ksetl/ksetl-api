package com.ksetl.triage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageTriageEvent {

    private Long messageTriageEventId;

    @NotEmpty
    private String bootstrapServers;

    @NotEmpty
    private String topic;

    @NotNull
    private Integer partition;

    @NotNull
    private Long offset;

    @NotEmpty
    private String errorMessage;

}