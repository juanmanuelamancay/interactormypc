package com.juanmanuelamancay.interactormypc.facade.read.dto;

import lombok.Data;

@Data
public class RequestReadFile {
    private String routeFile;
    private String fileName;
    private String type;
}
