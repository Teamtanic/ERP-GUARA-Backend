package com.guarajunior.guararp.config;

import org.alfresco.event.sdk.handling.filter.EventTypeFilter;
import org.alfresco.event.sdk.integration.EventChannels;
import org.alfresco.event.sdk.integration.filter.IntegrationEventFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.dsl.IntegrationFlowAdapter;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.stereotype.Component;

@Component
public class NewContentFlow extends IntegrationFlowAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewContentFlow.class);

    @Override
    protected IntegrationFlowDefinition<?> buildFlow() {
        return from(EventChannels.MAIN) // Listen to events coming from the Alfresco events channel
                .filter(IntegrationEventFilter.of(EventTypeFilter.NODE_CREATED)) // Filter events and select only node created events
                .handle(t -> LOGGER.info("File uploaded: {}", t.getPayload().toString())); // Handle event with a bit of logging
    }
}
