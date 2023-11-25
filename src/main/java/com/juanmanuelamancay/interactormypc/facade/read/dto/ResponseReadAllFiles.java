package com.juanmanuelamancay.interactormypc.facade.read.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseReadAllFiles {
    private List<String> filesNames;
    private boolean ok;
}
