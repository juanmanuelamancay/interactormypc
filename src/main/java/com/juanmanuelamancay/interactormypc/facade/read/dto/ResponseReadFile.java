package com.juanmanuelamancay.interactormypc.facade.read.dto;

import lombok.Data;

@Data
public class ResponseReadFile {
    private String contentFile;
    private boolean ok;
}
