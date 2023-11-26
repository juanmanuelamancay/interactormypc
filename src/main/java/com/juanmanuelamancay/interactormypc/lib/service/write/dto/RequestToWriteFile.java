package com.juanmanuelamancay.interactormypc.lib.service.write.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.FileRoute;
import lombok.Data;

@Data
public class RequestToWriteFile extends FileRoute {
    private String contentFile;
    private boolean reWrite;
}
