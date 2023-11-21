package com.guarajunior.guararp.api.dto.project.request;

import java.util.List;
import java.util.UUID;

public record ProjectOfferingsUpdateRequest(List<UUID> offeringIds) {
}