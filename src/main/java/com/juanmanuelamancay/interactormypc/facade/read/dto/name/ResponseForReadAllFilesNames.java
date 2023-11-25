package com.juanmanuelamancay.interactormypc.facade.read.dto.name;

import com.juanmanuelamancay.interactormypc.facade.dto.Response;
import lombok.Data;

import java.util.List;

@Data
public class ResponseForReadAllFilesNames extends Response {
    private List<String> filesNames;
}
