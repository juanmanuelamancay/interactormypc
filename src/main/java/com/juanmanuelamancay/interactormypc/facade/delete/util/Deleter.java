package com.juanmanuelamancay.interactormypc.facade.delete.util;

import com.juanmanuelamancay.interactormypc.facade.delete.dto.RequestToDeleteFile;
import com.juanmanuelamancay.interactormypc.facade.delete.dto.ResponseForDeleteFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Deleter {
    public ResponseForDeleteFile deleteFileInMyPc(RequestToDeleteFile requestToDeleteFile) {
        ResponseForDeleteFile responseForDeleteFile = new ResponseForDeleteFile();

        String route = requestToDeleteFile.getRoute();
        String fileName = requestToDeleteFile.getFileName();
        String type = requestToDeleteFile.getType();

        try {
            Path filePath = Paths.get(route, fileName + "." + type);

            // Verificar si el archivo existe
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                Files.delete(filePath);
                responseForDeleteFile.setSuccess(true);
            } else {
                responseForDeleteFile.setSuccess(false);
            }
        } catch (IOException e) {
            responseForDeleteFile.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
        }

        return responseForDeleteFile;
    }
}

