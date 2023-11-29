package com.juanmanuelamancay.interactormypc.lib.service.search.util;

import com.juanmanuelamancay.interactormypc.lib.service.search.dto.RequestToSearchContent;
import com.juanmanuelamancay.interactormypc.lib.service.search.dto.ResponseForSearchContent;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class SearchContentInAllFiles {
    List<String> fileNamesWithMatching;
    int lettersMatchedPerWord;
    boolean matchOverTheThreshold;

    public ResponseForSearchContent searchContentInMyPc(RequestToSearchContent requestToSearchContent) {
        return search(requestToSearchContent);
    }

    private ResponseForSearchContent search(RequestToSearchContent requestToSearchContent) {
        fileNamesWithMatching = new ArrayList<>();
        lettersMatchedPerWord = 0;
        matchOverTheThreshold = false;

        ResponseForSearchContent responseForSearchContent = new ResponseForSearchContent();

        String route = requestToSearchContent.getRoute();
        String contentToSearch = requestToSearchContent.getContentToSearch();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(route))) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            ArrayList<String> fileLineContentSeparateBySpace = separateBySpace(line.toLowerCase());
                            ArrayList<String> contentToSearchSeparateBySpace = separateBySpace(contentToSearch.toLowerCase());

                            ArrayList<String> fileLineContentWithoutEmptyElements = removeEmptyElements(fileLineContentSeparateBySpace);
                            ArrayList<String> contentToSearchWithoutEmptyElements = removeEmptyElements(contentToSearchSeparateBySpace);

                            executeMainLogic(filePath, requestToSearchContent.isIntense(), fileLineContentWithoutEmptyElements, contentToSearchWithoutEmptyElements);
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer el contenido del archivo: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            responseForSearchContent.setSuccess(true);
            responseForSearchContent.setFileNames(removeDuplicates(fileNamesWithMatching));

        } catch (IOException e) {
            responseForSearchContent.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
        }
        return responseForSearchContent;
    }

    private void executeMainLogic(Path filePath, boolean intense, ArrayList<String> fileLineContentWithoutEmptyElements, ArrayList<String> contentToSearchWithoutEmptyElements) {
        if (intense) {
            searchIntense(filePath, fileLineContentWithoutEmptyElements, contentToSearchWithoutEmptyElements);
        } else {
            searchBasic(filePath, fileLineContentWithoutEmptyElements, contentToSearchWithoutEmptyElements);
        }
    }

    private void searchBasic(Path filePath, ArrayList<String> fileLine, ArrayList<String> contentToSearch) {
        for (String wordPerFileLine : fileLine) {
            for (String wordPerContentToSearch : contentToSearch) {
                if (wordPerFileLine.contains(wordPerContentToSearch)) {
                    fileNamesWithMatching.add(filePath.getFileName().toString().replaceAll(".txt", ""));
                }
            }
        }
    }

    private void searchIntense(Path filePath, ArrayList<String> fileLineContentWithoutEmptyElements, ArrayList<String> contentToSearchWithoutEmptyElements) {
        boolean passMatchThreshold = getMatchThreshold(fileLineContentWithoutEmptyElements, contentToSearchWithoutEmptyElements);
        if (passMatchThreshold) {
            fileNamesWithMatching.add(filePath.getFileName().toString().replaceAll(".txt", ""));
        }
    }

    private boolean getMatchThreshold(ArrayList<String> fileLine, ArrayList<String> contentToSearch){
        for (String wordPerFileLine : fileLine) {
            for (String wordPerContentToSearch : contentToSearch) {
                lettersMatchedPerWord = countMatchingByLetter(wordPerFileLine, wordPerContentToSearch);
                matchOverTheThreshold = validateMatchThreshold(lettersMatchedPerWord, wordPerFileLine.length(), wordPerContentToSearch.length());
                if (matchOverTheThreshold) {
                    return true;
                }
            }
        }
        return false;
    }

    private int countMatchingByLetter(String wordPerFileLine, String wordPerContentToSearch) {
        int counter = 0;
        String[] lettersForEachWordOfTheFileLine = wordPerFileLine.split("");
        String[] lettersForEachWordOfTheContentToSearch = wordPerContentToSearch.split("");

        for (String fileLineLetter : lettersForEachWordOfTheFileLine) {
            for (String contentToSearchLetter : lettersForEachWordOfTheContentToSearch) {
                if (fileLineLetter.contains(contentToSearchLetter)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private boolean validateMatchThreshold(int lettersPartsCounter, int partFileLineLength, int partContentToSearchLength) {
        int absoluteDifference = Math.abs(partFileLineLength - partContentToSearchLength);
        return lettersPartsCounter / 2 > 0 && lettersPartsCounter / 2 > absoluteDifference;
    }

    private ArrayList<String> separateBySpace(String sentence) {
        String[] separateWordsArray = sentence.split("\\s+");
        return new ArrayList<>(Arrays.asList(separateWordsArray));
    }

    private ArrayList<String> removeEmptyElements(ArrayList<String> listToRemove) {
        listToRemove.removeIf(element -> element.trim().isEmpty());
        return listToRemove;
    }

    public static List<String> removeDuplicates(List<String> inputList) {
        Set<String> uniqueSet = new HashSet<>(inputList);
        return new ArrayList<>(uniqueSet);
    }
}
