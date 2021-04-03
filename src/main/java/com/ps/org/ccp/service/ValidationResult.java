package com.ps.org.ccp.service;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ValidationResult {
    private List<String> errors;
    private boolean isValid;
}
