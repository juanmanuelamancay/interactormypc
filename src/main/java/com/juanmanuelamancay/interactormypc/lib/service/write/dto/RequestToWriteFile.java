package com.juanmanuelamancay.interactormypc.lib.service.write.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.FileRoute;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestToWriteFile extends FileRoute {
    private String contentFile;
    private boolean reWrite;

}
