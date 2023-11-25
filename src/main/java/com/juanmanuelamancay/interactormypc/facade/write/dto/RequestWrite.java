package com.juanmanuelamancay.interactormypc.facade.write.dto;

import lombok.Data;

@Data
public class RequestWrite {
    private String routeFile;
    private String fileName;
    private String type;
    private String contentFile;
    private boolean reWrite;
}
