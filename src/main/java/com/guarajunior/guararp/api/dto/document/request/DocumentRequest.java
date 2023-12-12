package com.guarajunior.guararp.api.dto.document.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class DocumentRequest {
    @NotNull(message = "O Id de DocumentType precisa ser passado")
    private UUID documentTypeId;
    private String title;
    private String description;

    @NotNull(message = "'folderPath' está faltando. Defina o caminho do diretório que o arquivo será salvo")
    private String folderPath;

    @NotNull
    private MultipartFile file;
}
