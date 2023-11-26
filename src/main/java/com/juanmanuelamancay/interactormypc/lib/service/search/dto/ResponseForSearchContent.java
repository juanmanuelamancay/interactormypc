package com.juanmanuelamancay.interactormypc.lib.service.search.dto;

import com.juanmanuelamancay.interactormypc.lib.dto.ServiceResponse;
import lombok.Data;
import java.util.List;

@Data
public class ResponseForSearchContent extends ServiceResponse {
    private List<String> fileNames;
}
