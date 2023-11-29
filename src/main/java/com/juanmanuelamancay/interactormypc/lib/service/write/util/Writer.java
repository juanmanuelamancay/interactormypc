package com.juanmanuelamancay.interactormypc.lib.service.write.util;

import com.juanmanuelamancay.interactormypc.lib.service.write.dto.RequestToWriteFile;
import com.juanmanuelamancay.interactormypc.lib.service.write.dto.ResponseForWriteFile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;

@Component
public class Writer {
    public ResponseForWriteFile writeFileInMyPc(RequestToWriteFile requestToWriteFile) {
        ResponseForWriteFile responseForWriteFile = new ResponseForWriteFile();

        try {
            String filePath = requestToWriteFile.getRoute() + File.separator + requestToWriteFile.getFileName() + "." + requestToWriteFile.getType();
            String content = requestToWriteFile.getContentFile();
            Charset charset = Charset.forName("UTF-8");

            // Verificar si el archivo ya existe
            File file = new File(filePath);
            if (file.exists()) {
                if (!requestToWriteFile.isReWrite()) {
                    responseForWriteFile.setDuplicateFileName(true);
                    responseForWriteFile.setSuccess(true);
                    return responseForWriteFile;
                }
            }

            // El archivo no existe, proceder con la escritura
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, charset)) {
                if (content != null) {
                    // Escribe el contenido en el archivo
                    osw.write(content);
                }
            }

            responseForWriteFile.setSuccess(true);
            responseForWriteFile.setDuplicateFileName(false);
        } catch (IOException e) {
            responseForWriteFile.setSuccess(false);
            responseForWriteFile.setDuplicateFileName(false);
        }

        return responseForWriteFile;
    }
}