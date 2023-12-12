package com.guarajunior.guararp.config;

import com.guarajunior.guararp.alfresco.producer.CreateSiteProducer;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AlfrescoConfig {
    final private CreateSiteProducer createSiteProducer;

    public AlfrescoConfig(CreateSiteProducer createSiteProducer) {
        this.createSiteProducer = createSiteProducer;
    }

    @PostConstruct
    public void setupAlfrescoSite() throws IOException {
        try {
            createSiteProducer.execute("guararp", "GuaraRP", "Espaço para gestão de documentos da Empresa Guará Junior");
        } catch (FeignException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
