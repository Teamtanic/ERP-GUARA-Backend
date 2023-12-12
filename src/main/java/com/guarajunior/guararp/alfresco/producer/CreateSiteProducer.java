package com.guarajunior.guararp.alfresco.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.Site;
import org.alfresco.core.model.SiteBodyCreate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateSiteProducer {
    final SitesApi sitesApi;

    public Site execute(String siteId, String title, String description) throws IOException {
        Site site = Objects.requireNonNull(sitesApi.createSite(
                new SiteBodyCreate()
                        .id(siteId)
                        .title(title)
                        .description(description)
                        .visibility(SiteBodyCreate.VisibilityEnum.PUBLIC),
                null, null, null).getBody()).getEntry();

        log.info("Created site: {}", site);
        return site;
    }
}
