package com.juanmanuelamancay.interactormypc.facade.write.util;

import com.juanmanuelamancay.interactormypc.facade.write.dto.RequestToWriteFile;
import com.juanmanuelamancay.interactormypc.facade.write.dto.ResponseForWriteFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class Writer {
    public ResponseForWriteFile writeFileInMyPc(RequestToWriteFile requestToWriteFile) {
        ResponseForWriteFile responseForWriteFile = new ResponseForWriteFile();

        try {
            String filePath = requestToWriteFile.getRoute() + File.separator + requestToWriteFile.getFileName() + "." + requestToWriteFile.getType();
            String content = requestToWriteFile.getContentFile();
            // Verificar si el archivo ya existe
            File file = new File(filePath);
            if (file.exists()) {
                if(!requestToWriteFile.isReWrite()){
                    responseForWriteFile.setDuplicateFileName(true);
                    responseForWriteFile.setSuccess(true);
                    return responseForWriteFile;
                }
            }

            // El archivo no existe, proceder con la escritura
            FileWriter fileWriter = new FileWriter(filePath);
            if (content != null) {
                fileWriter.write(content);
            }
            fileWriter.close();

            responseForWriteFile.setSuccess(true);
            responseForWriteFile.setDuplicateFileName(false);
        } catch (IOException e) {
            responseForWriteFile.setSuccess(false);
            responseForWriteFile.setDuplicateFileName(false);
        }

        return responseForWriteFile;
    }
}

