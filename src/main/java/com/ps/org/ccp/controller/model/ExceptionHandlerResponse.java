package com.ps.org.ccp.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ExceptionHandlerResponse {

    private String message;

    private List<String> errors;

}
