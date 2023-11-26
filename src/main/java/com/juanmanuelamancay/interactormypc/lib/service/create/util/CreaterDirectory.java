package com.juanmanuelamancay.interactormypc.lib.service.create.util;

import com.juanmanuelamancay.interactormypc.lib.service.create.dto.RequestToCreateDirectory;
import com.juanmanuelamancay.interactormypc.lib.service.create.dto.ResponseForCreateDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CreaterDirectory {
    public ResponseForCreateDirectory createDirectoryInMyPc (RequestToCreateDirectory requestToCreateDirectory){
        ResponseForCreateDirectory responseForCreateDirectory = new ResponseForCreateDirectory();
        String route = requestToCreateDirectory.getRoute();
        String directoryName = requestToCreateDirectory.getDirectoryName();

        try {
            Path directoryPath = Paths.get(route, directoryName);

            // Verificar si el directorio ya existe
            if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
                Files.createDirectory(directoryPath);
                responseForCreateDirectory.setSuccess(true);
            } else {
                responseForCreateDirectory.setSuccess(false);
            }
        } catch (IOException e) {
            responseForCreateDirectory.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
        }

        return responseForCreateDirectory;
    }
}
