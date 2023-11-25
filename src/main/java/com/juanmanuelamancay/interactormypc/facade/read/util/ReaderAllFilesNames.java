package com.juanmanuelamancay.interactormypc.facade.read.util;

import com.juanmanuelamancay.interactormypc.facade.read.dto.name.RequestToReadAllFilesNames;
import com.juanmanuelamancay.interactormypc.facade.read.dto.name.ResponseForReadAllFilesNames;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReaderAllFilesNames {
    public ResponseForReadAllFilesNames readAllFilesInMyPc(RequestToReadAllFilesNames requestToReadAllFilesNames) {
        ResponseForReadAllFilesNames responseForReadAllFilesNames = new ResponseForReadAllFilesNames();
        String route = requestToReadAllFilesNames.getRoute();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(route))) {
            List<String> fileNames = new ArrayList<>();
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    String fileNameWithoutExtension = removeFileExtension(path);
                    fileNames.add(fileNameWithoutExtension);
                }
            }
            responseForReadAllFilesNames.setFilesNames(fileNames);
            responseForReadAllFilesNames.setSuccess(true);
        } catch (IOException e) {
            responseForReadAllFilesNames.setSuccess(false);
            System.out.println("error: " + e.getMessage());
        }

        return responseForReadAllFilesNames;
    }

    private String removeFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}
