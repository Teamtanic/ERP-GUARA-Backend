package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.alfresco.producer.CreateSiteProducer;
import com.guarajunior.guararp.api.dto.document.request.DocumentRequest;
import com.guarajunior.guararp.api.dto.document.response.DocumentResponse;
import com.guarajunior.guararp.domain.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alfresco.core.model.Site;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    final private CreateSiteProducer createSiteProducer;

    @PostMapping("/documentos/criar-site")
    public ResponseEntity<Site> createSite() throws IOException {
        Site site = createSiteProducer.execute("guararp");
        return ResponseEntity.created(URI.create(site.getId())).body(site);
    }

    @GetMapping("/projeto/{projectId}/documentos/")
    public ResponseEntity<?> getAllDocumentsByProjectId(@PathVariable UUID projectId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(documentService.getAllDocuments(projectId, page, size));
    }

    @PostMapping("/projeto/{projectId}/documentos/upload")
    public ResponseEntity<DocumentResponse> handleFileUpload(@PathVariable UUID projectId, @Valid @ModelAttribute DocumentRequest documentRequest) {
        return ResponseEntity.ok(documentService.handleFileUpload(projectId, documentRequest));
    }

    @GetMapping("/documentos/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable UUID documentId) {
        return ResponseEntity.ok(documentService.getDocumentById(documentId));
    }
}
