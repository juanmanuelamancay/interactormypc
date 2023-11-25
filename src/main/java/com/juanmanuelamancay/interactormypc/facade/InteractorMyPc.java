package com.juanmanuelamancay.interactormypc.facade;

import com.juanmanuelamancay.interactormypc.facade.read.dto.RequestReadAllFiles;
import com.juanmanuelamancay.interactormypc.facade.read.dto.RequestReadFile;
import com.juanmanuelamancay.interactormypc.facade.read.dto.ResponseReadAllFiles;
import com.juanmanuelamancay.interactormypc.facade.read.dto.ResponseReadFile;
import com.juanmanuelamancay.interactormypc.facade.read.util.ReadFile;
import com.juanmanuelamancay.interactormypc.facade.read.util.ReaderAllFiles;
import com.juanmanuelamancay.interactormypc.facade.write.dto.RequestWrite;
import com.juanmanuelamancay.interactormypc.facade.write.dto.ResponseWrite;
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
    private ReaderAllFiles readerAllFiles;

    @Autowired
    private ReadFile readFile;

    @GetMapping("")
    public String obtenerSaludo() {
        return "¡Interactor My PC!";
    }

    @PostMapping("/writeFile")
    public ResponseEntity<ResponseWrite> executeWrite(@RequestBody RequestWrite requestWrite) {
        ResponseWrite responseWrite = writer.writeFileInMyPc(requestWrite);

        if (responseWrite.isOk()) {
            return new ResponseEntity<>(responseWrite, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseWrite, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/readAllFiles")
    public ResponseEntity<ResponseReadAllFiles> executeReadAllFiles(@RequestBody RequestReadAllFiles requestReadAllFiles) {
        ResponseReadAllFiles responseReadAllFiles = readerAllFiles.readAllFilesInMyPc(requestReadAllFiles);

        if (responseReadAllFiles.isOk()) {
            return new ResponseEntity<>(responseReadAllFiles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseReadAllFiles, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/readFile")
    public ResponseEntity<ResponseReadFile> executeReadFile(@RequestBody RequestReadFile requestReadFile) {
        ResponseReadFile responseReadFile = readFile.readFileInMyPc(requestReadFile);

        if (responseReadFile.isOk()) {
            return new ResponseEntity<>(responseReadFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseReadFile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
