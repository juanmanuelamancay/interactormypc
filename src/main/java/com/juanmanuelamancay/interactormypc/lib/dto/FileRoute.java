package com.juanmanuelamancay.interactormypc.lib.dto;

import lombok.Data;

@Data
public abstract class FileRoute extends DirectoryRoute {
    protected String fileName;
    protected String type;
}
