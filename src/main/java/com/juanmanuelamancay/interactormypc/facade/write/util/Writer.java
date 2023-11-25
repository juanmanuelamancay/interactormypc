package com.juanmanuelamancay.interactormypc.facade.write.util;

import com.juanmanuelamancay.interactormypc.facade.write.dto.RequestWrite;
import com.juanmanuelamancay.interactormypc.facade.write.dto.ResponseWrite;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class Writer {
    public ResponseWrite writeFileInMyPc(RequestWrite requestWrite) {
        ResponseWrite responseWrite = new ResponseWrite();

        try {
            String filePath = requestWrite.getRouteFile() + File.separator + requestWrite.getFileName() + "." + requestWrite.getType();
            String content = requestWrite.getContentFile();

            // Verificar si el archivo ya existe
            File file = new File(filePath);
            if (file.exists()) {
                if(!requestWrite.isReWrite()){
                    responseWrite.setDuplicateFileName(true);
                    responseWrite.setOk(true);
                    return responseWrite;
                }
            }

            // El archivo no existe, proceder con la escritura
            FileWriter fileWriter = new FileWriter(filePath);
            if (content != null) {
                fileWriter.write(content);
            }
            fileWriter.close();

            responseWrite.setOk(true);
            responseWrite.setDuplicateFileName(false);
        } catch (IOException e) {
            responseWrite.setOk(false);
            responseWrite.setDuplicateFileName(false);
        }

        return responseWrite;
    }
}

