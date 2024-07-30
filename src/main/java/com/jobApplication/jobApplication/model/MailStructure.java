package com.jobApplication.jobApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailStructure {
    private String subject;

    private String message;

    private String buttonText;

    private String buttonUrl;
}
