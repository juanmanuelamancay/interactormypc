package com.juanmanuelamancay.interactormypc.lib.service.create.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.DirectoryRoute;
import lombok.Data;

@Data
public class RequestToCreateDirectory extends DirectoryRoute {
    private String directoryName;
}
