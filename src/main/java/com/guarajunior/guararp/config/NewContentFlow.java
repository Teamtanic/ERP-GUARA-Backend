package com.guarajunior.guararp.config;

import org.alfresco.event.sdk.handling.filter.EventTypeFilter;
import org.alfresco.event.sdk.integration.EventChannels;
import org.alfresco.event.sdk.integration.filter.IntegrationEventFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.dsl.IntegrationFlowAdapter;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class NewContentFlow extends IntegrationFlowAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewContentFlow.class);

    @Override
    @NonNull
    protected IntegrationFlowDefinition<?> buildFlow() {
        return from(EventChannels.MAIN)
                .filter(IntegrationEventFilter.of(EventTypeFilter.NODE_CREATED))
                .handle(t -> LOGGER.info("File uploaded: {}", t.getPayload().toString()));
    }
}
