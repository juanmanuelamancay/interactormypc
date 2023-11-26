package com.juanmanuelamancay.interactormypc.lib.service.read.dto.content;

import com.juanmanuelamancay.interactormypc.lib.dto.ServiceResponse;
import lombok.Data;

@Data
public class ResponseForReadFileContent extends ServiceResponse {
    private String contentFile;

}
