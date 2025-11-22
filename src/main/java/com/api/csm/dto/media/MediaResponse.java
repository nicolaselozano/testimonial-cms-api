package com.api.csm.dto.media;

import com.api.csm.utils.MediaType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public record MediaResponse (
    UUID id,
    String url,
    MediaType type
){}
