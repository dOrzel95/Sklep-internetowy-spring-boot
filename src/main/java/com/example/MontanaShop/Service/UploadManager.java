package com.example.MontanaShop.Service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
@Service
public class UploadManager {
   public String uploadFile(MultipartFile file){
       File uploadDirectory = new File("uploads");
       uploadDirectory.mkdirs();
       String pathFileOnServer="";
       try {
           File oFile = new File("uploads/" + file.getOriginalFilename());
           OutputStream os = new FileOutputStream(oFile);
           InputStream inputStream = file.getInputStream();
           IOUtils.copy(inputStream, os);
           pathFileOnServer = oFile.getAbsolutePath();
           os.close();
           inputStream.close();
           pathFileOnServer = oFile.getAbsolutePath();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return pathFileOnServer;
   }
}
