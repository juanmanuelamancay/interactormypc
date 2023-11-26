package com.juanmanuelamancay.interactormypc.lib.service.search.util;

import com.juanmanuelamancay.interactormypc.lib.service.search.dto.RequestToSearchContent;
import com.juanmanuelamancay.interactormypc.lib.service.search.dto.ResponseForSearchContent;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchContentInAllFiles {
    public ResponseForSearchContent serarchContentInMyPc(RequestToSearchContent requestToSearchContent){
        ResponseForSearchContent responseForSearchContent = new ResponseForSearchContent();
        responseForSearchContent.setSuccess(false);
        responseForSearchContent.setFileNames(new ArrayList<>());

        switch (requestToSearchContent.getSearchType()){
            case "basic":
                return serarchBasic(requestToSearchContent);
            default:
                return responseForSearchContent;
        }
    }

    private ResponseForSearchContent serarchBasic (RequestToSearchContent requestToSearchContent){
        ResponseForSearchContent responseForSearchContent = new ResponseForSearchContent();
        List<String> fileNamesWithContent = new ArrayList<>();

        String route = requestToSearchContent.getRoute();
        String contentToSearch = requestToSearchContent.getContentToSearch();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(route))) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    if (containsContent(filePath, contentToSearch)) {
                        fileNamesWithContent.add(filePath.getFileName().toString().replaceAll(".txt", ""));
                    }
                }
            }

            responseForSearchContent.setFileNames(fileNamesWithContent);
            responseForSearchContent.setSuccess(true);
        } catch (IOException e) {
            responseForSearchContent.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
        }

        return responseForSearchContent;
    }

    private boolean containsContent(Path filePath, String contentToSearch) throws IOException {
        String lowerCaseContentToSearch = contentToSearch.toLowerCase();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String lowerCaseLine = line.toLowerCase();
                if (lowerCaseLine.contains(lowerCaseContentToSearch)) {
                    return true;
                }
            }
        }
        return false;
    }

}
