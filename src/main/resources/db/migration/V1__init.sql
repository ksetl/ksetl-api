CREATE TABLE data_value_mapping
(
    data_value_mapping_id SERIAL PRIMARY KEY,
    value1 TEXT NOT NULL,
    value2 TEXT NOT NULL
);
ALTER SEQUENCE data_value_mapping_data_value_mapping_id_seq RESTART 1000000;

CREATE TABLE message_triage_event
(
    message_triage_event_id BIGSERIAL PRIMARY KEY,
    bootstrap_servers TEXT NOT NULL,
    topic TEXT NOT NULL,
    partition INTEGER NOT NULL,
    offset_value BIGINT NOT NULL,
    error_message TEXT NOT NULL
);
ALTER SEQUENCE message_triage_event_message_triage_event_id_seq RESTART 1000000;