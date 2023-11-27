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
import java.util.*;

@Component
public class SearchContentInAllFiles {

    List<String> fileNamesWithCoincidence = new ArrayList<>();
    int wordsPartsCounter = 0;
    boolean existCoincidence;

    public ResponseForSearchContent searchContentInMyPc(RequestToSearchContent requestToSearchContent){
        return search(requestToSearchContent);
    }


    private ResponseForSearchContent search(RequestToSearchContent requestToSearchContent) {
        fileNamesWithCoincidence = new ArrayList<>();
        ResponseForSearchContent responseForSearchContent = new ResponseForSearchContent();

        String route = requestToSearchContent.getRoute();
        String contentToSearch = requestToSearchContent.getContentToSearch();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(route))) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                        String line;
                        int lineNumber = 0; // con esta avriable pintamos el lugar encontrado si deseamos
                        while ((line = reader.readLine()) != null) {
                            lineNumber++;
                            ArrayList<String> fileLineContentSeparateBySpace = separateBySpace(line.toLowerCase());
                            ArrayList<String> contentToSearchSeparateBySpace = separateBySpace(contentToSearch.toLowerCase());

                            ArrayList<String> fileLineContentWithoutEmptyParts = removeEmptyParts(fileLineContentSeparateBySpace);
                            ArrayList<String> contentToSearchWithoutEmptyParts = removeEmptyParts(contentToSearchSeparateBySpace);

                            if(requestToSearchContent.isIntense()){
                                boolean passCoincidenceThreshold = searchIntense(fileLineContentWithoutEmptyParts,contentToSearchWithoutEmptyParts);
                                if(passCoincidenceThreshold){
                                    fileNamesWithCoincidence.add(filePath.getFileName().toString().replaceAll(".txt", ""));
                                }
                            }else{
                                searchBasic(filePath, fileLineContentWithoutEmptyParts,contentToSearchWithoutEmptyParts);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer el contenido del archivo: " + e.getMessage());
                    }
                }
            }

            responseForSearchContent.setSuccess(true);
            responseForSearchContent.setFileNames(removeDuplicates(fileNamesWithCoincidence));

        } catch (IOException e) {
            responseForSearchContent.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
        }
        return responseForSearchContent;
    }

    private void searchBasic(Path filePath, ArrayList<String> fileLine, ArrayList<String> contentToSearch) {
        for (String partFileLine : fileLine) {
            for (String partContentToSearch : contentToSearch) {
                if (partFileLine.contains(partContentToSearch)) {
                    fileNamesWithCoincidence.add(filePath.getFileName().toString().replaceAll(".txt", ""));
                }
            }
        }
    }


    private boolean searchIntense(ArrayList<String> fileLine, ArrayList<String> contentToSearch){
        for(String fileLinePart: fileLine){
            for (String contentToSearchPart: contentToSearch){
                wordsPartsCounter = countCoincidencesByWord(fileLinePart, contentToSearchPart);
                existCoincidence = validateCoincidenceThreshold(wordsPartsCounter, fileLinePart.length(), contentToSearchPart.length());
                if(existCoincidence){
                    return true;
                }
            }
        }
        return false;
    }

    private int countCoincidencesByWord(String partFileLine, String partContentToSearch){
        int counter = 0;
        String[] wordPartFileLine = partFileLine.split("");
        String[] wordPartContentToSearch = partContentToSearch.split("");
        for(String wordForFileLine: wordPartFileLine){
            for (String wordForContentToSearch: wordPartContentToSearch){
                if(wordForFileLine.contains(wordForContentToSearch)){
                    counter++;
                }
            }
        }
        return counter;
    }

    private boolean validateCoincidenceThreshold(int wordsPartsCounter, int partFileLineLength, int partContentToSearchLength){
        int absoluteDifference = Math.abs(partFileLineLength - partContentToSearchLength);
        return wordsPartsCounter/2 > 0 && wordsPartsCounter/2 > absoluteDifference;
    }

    private ArrayList<String> separateBySpace(String sentence){
        String[] separateWordsArray = sentence.split("\\s+");
        return new ArrayList<>(Arrays.asList(separateWordsArray));
    }

    private ArrayList<String> removeEmptyParts(ArrayList<String> listToRemove){
        listToRemove.removeIf(part -> part.trim().isEmpty());
        return listToRemove;
    }

    public static List<String> removeDuplicates(List<String> inputList) {
        Set<String> uniqueSet = new HashSet<>(inputList);
        return new ArrayList<>(uniqueSet);
    }
}
