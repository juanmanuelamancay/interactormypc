package com.juanmanuelamancay.interactormypc.facade;

import com.juanmanuelamancay.interactormypc.facade.delete.dto.RequestToDeleteFile;
import com.juanmanuelamancay.interactormypc.facade.delete.dto.ResponseForDeleteFile;
import com.juanmanuelamancay.interactormypc.facade.delete.util.Deleter;
import com.juanmanuelamancay.interactormypc.facade.read.dto.name.RequestToReadAllFilesNames;
import com.juanmanuelamancay.interactormypc.facade.read.dto.content.RequestToReadFileContent;
import com.juanmanuelamancay.interactormypc.facade.read.dto.name.ResponseForReadAllFilesNames;
import com.juanmanuelamancay.interactormypc.facade.read.dto.content.ResponseForReadFileContent;
import com.juanmanuelamancay.interactormypc.facade.read.util.ReaderFileContent;
import com.juanmanuelamancay.interactormypc.facade.read.util.ReaderAllFilesNames;
import com.juanmanuelamancay.interactormypc.facade.write.dto.RequestToWriteFile;
import com.juanmanuelamancay.interactormypc.facade.write.dto.ResponseForWriteFile;
import com.juanmanuelamancay.interactormypc.facade.write.util.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/InteractorMyPc")
public class InteractorMyPc {

    @Autowired
    private Writer writer;

    @Autowired
    private ReaderAllFilesNames readerAllFilesNames;

    @Autowired
    private ReaderFileContent readerFileContent;

    @Autowired
    private Deleter deleter;

    @GetMapping("")
    public String obtenerSaludo() {
        return "¡Interactor My PC!";
    }

    @PostMapping("/writeFile")
    public ResponseEntity<ResponseForWriteFile> executeWriteFile(@RequestBody RequestToWriteFile requestToWriteFile) {
        ResponseForWriteFile responseForWriteFile = writer.writeFileInMyPc(requestToWriteFile);

        if (responseForWriteFile.isSuccess()) {
            return new ResponseEntity<>(responseForWriteFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseForWriteFile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/readAllFiles")
    public ResponseEntity<ResponseForReadAllFilesNames> executeReadAllFiles(@RequestBody RequestToReadAllFilesNames requestToReadAllFilesNames) {
        ResponseForReadAllFilesNames responseForReadAllFilesNames = readerAllFilesNames.readAllFilesInMyPc(requestToReadAllFilesNames);

        if (responseForReadAllFilesNames.isSuccess()) {
            return new ResponseEntity<>(responseForReadAllFilesNames, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseForReadAllFilesNames, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/readFile")
    public ResponseEntity<ResponseForReadFileContent> executeReadFile(@RequestBody RequestToReadFileContent requestToReadFileContent) {
        ResponseForReadFileContent responseForReadFileContent = readerFileContent.readFileInMyPc(requestToReadFileContent);

        if (responseForReadFileContent.isSuccess()) {
            return new ResponseEntity<>(responseForReadFileContent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseForReadFileContent, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<ResponseForDeleteFile> executeDeleteFile(@RequestBody RequestToDeleteFile requestToDeleteFile) {
        ResponseForDeleteFile responseForDeleteFile = deleter.deleteFileInMyPc(requestToDeleteFile);

        if (responseForDeleteFile.isSuccess()) {
            return new ResponseEntity<>(responseForDeleteFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseForDeleteFile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
