package com.juanmanuelamancay.interactormypc.lib.service.search.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.DirectoryRoute;
import lombok.Data;

@Data
public class RequestToSearchContent extends DirectoryRoute {
    private String contentToSearch;
}
