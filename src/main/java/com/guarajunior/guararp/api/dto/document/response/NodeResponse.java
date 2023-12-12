package com.guarajunior.guararp.api.dto.document.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alfresco.core.model.Node;

@Data
@EqualsAndHashCode(callSuper = true)
public class NodeResponse extends Node {
    private DocumentResponse document;
}
