package com.juanmanuelamancay.interactormypc.facade.read.dto.content;

import com.juanmanuelamancay.interactormypc.facade.dto.Response;
import lombok.Data;

@Data
public class ResponseForReadFileContent extends Response {
    private String contentFile;

}
