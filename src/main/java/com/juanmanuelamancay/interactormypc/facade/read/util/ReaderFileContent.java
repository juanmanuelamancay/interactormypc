package com.juanmanuelamancay.interactormypc.facade.read.util;

import com.juanmanuelamancay.interactormypc.facade.read.dto.content.RequestToReadFileContent;
import com.juanmanuelamancay.interactormypc.facade.read.dto.content.ResponseForReadFileContent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ReaderFileContent {
    public ResponseForReadFileContent readFileInMyPc(RequestToReadFileContent requestToReadFileContent) {
        ResponseForReadFileContent responseForReadFileContent = new ResponseForReadFileContent();

        String route = requestToReadFileContent.getRoute();
        String fileName = requestToReadFileContent.getFileName();
        String type = requestToReadFileContent.getType();

        try {
            Path filePath = Paths.get(route, fileName + "." + type);
            if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                String content = new String(Files.readAllBytes(filePath));
                responseForReadFileContent.setContentFile(content);
                responseForReadFileContent.setSuccess(true);
            } else {
                responseForReadFileContent.setSuccess(false);
            }
        } catch (IOException e) {
            responseForReadFileContent.setSuccess(false);
            System.out.println("error: " + e.getMessage());
        }

        return responseForReadFileContent;
    }
}

