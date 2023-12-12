package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.document.request.DocumentRequest;
import com.guarajunior.guararp.api.dto.document.response.NodeResponse;
import com.guarajunior.guararp.domain.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/documentos")
    public ResponseEntity<?> listFolderItems(@RequestParam(defaultValue = "") String folderPath) {
        return ResponseEntity.ok(documentService.listFolderItems(folderPath));
    }

    @PostMapping("/documentos/upload")
    public ResponseEntity<NodeResponse> handleFileUpload(@Valid @ModelAttribute DocumentRequest documentRequest) {
        return ResponseEntity.ok(documentService.handleFileUpload(documentRequest));
    }

    @GetMapping("/documentos/{documentId}")
    public ResponseEntity<NodeResponse> getDocumentById(@PathVariable UUID documentId) {
        return ResponseEntity.ok(documentService.getDocumentById(documentId));
    }

    @GetMapping("/documentos/{documentId}/content")
    public ResponseEntity<Resource> getDocumentContentById(@PathVariable String documentId) {
        return documentService.getDocumentContentById(documentId);
    }
}
