package com.juanmanuelamancay.interactormypc.lib.service.write.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.ServiceResponse;
import lombok.Data;

@Data
public class ResponseForWriteFile extends ServiceResponse {
    private boolean duplicateFileName;
}
