package com.meoying.ai.ielts.web.writing;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ExamineReq {
    @NotEmpty
    private String question;
    @NotEmpty
    private String writing;
}
