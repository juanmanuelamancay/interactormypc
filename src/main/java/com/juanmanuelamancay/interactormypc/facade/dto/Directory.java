package com.juanmanuelamancay.interactormypc.facade.dto;

import lombok.Data;

@Data
public abstract class Directory {
    protected String route;
    protected String fileName;
    protected String type;
}
