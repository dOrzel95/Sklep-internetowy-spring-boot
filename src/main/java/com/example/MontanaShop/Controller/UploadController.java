package com.example.MontanaShop.Controller;

import com.example.MontanaShop.AuthorizationTokenns.AccesToken;
import com.example.MontanaShop.AuthorizationTokenns.RefreshToken;
import com.example.MontanaShop.Configuration.Confirm;
import com.example.MontanaShop.Exception.BadPasswordException;
import com.example.MontanaShop.Exception.InactiveAccountException;
import com.example.MontanaShop.Exception.UsernameNotExistException;
import com.example.MontanaShop.Model.Client;
import com.example.MontanaShop.Model.ShoppingCart;
import com.example.MontanaShop.Model.Token;
import com.example.MontanaShop.Repository.ClientRepository;
import com.example.MontanaShop.Repository.TokenRepository;
import com.example.MontanaShop.Service.ClientManager;
import com.example.MontanaShop.Service.JwTokenManager;
import com.example.MontanaShop.Service.ShoppingCartManager;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.*;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/montanashop/")
public class UploadController {
    @Autowired
    public UploadController() {

    }
    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<String> handleFile(@RequestPart(name = "photo") MultipartFile file) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("File send error: " + e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(pathFileOnServer, HttpStatus.OK);
    }





}

