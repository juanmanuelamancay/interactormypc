package com.juanmanuelamancay.interactormypc.lib.service.read.dto.name;

import com.juanmanuelamancay.interactormypc.lib.dto.ServiceResponse;
import lombok.Data;

import java.util.List;

@Data
public class ResponseForReadAllFilesNames extends ServiceResponse {
    private List<String> filesNames;
}
