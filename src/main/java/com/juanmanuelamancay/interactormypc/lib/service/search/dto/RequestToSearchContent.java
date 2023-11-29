package com.juanmanuelamancay.interactormypc.lib.service.search.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.DirectoryRoute;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestToSearchContent extends DirectoryRoute {
    private String contentToSearch;
    private boolean intense;
}
