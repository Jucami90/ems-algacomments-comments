package com.algaworks.algacomments.comments.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModerationOutput {
    private boolean approved;
    private String reason;
}
