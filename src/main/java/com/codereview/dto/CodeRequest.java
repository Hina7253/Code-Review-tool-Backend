// CodeRequest.java
package com.codereview.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CodeRequest {
    @NotBlank(message = "Code cannot be empty")
    private String code;
}