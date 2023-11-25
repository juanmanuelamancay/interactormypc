package com.juanmanuelamancay.interactormypc.facade.write.dto;

import lombok.Data;

@Data
public class ResponseWrite {
    private boolean ok;
    private boolean duplicateFileName;
}
