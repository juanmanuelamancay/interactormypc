package com.juanmanuelamancay.interactormypc.facade.write.dto;

import com.juanmanuelamancay.interactormypc.facade.dto.Directory;
import lombok.Data;

@Data
public class RequestToWriteFile extends Directory {
    private String contentFile;
    private boolean reWrite;
}
