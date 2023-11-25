package com.juanmanuelamancay.interactormypc.facade.write.dto;

import com.juanmanuelamancay.interactormypc.facade.dto.Response;
import lombok.Data;

@Data
public class ResponseForWriteFile extends Response {
    private boolean duplicateFileName;
}
