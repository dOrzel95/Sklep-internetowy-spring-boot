package com.example.MontanaShop.Controller;


import com.example.MontanaShop.AuthorizationTokenns.AccesToken;
import com.example.MontanaShop.Configuration.Confirm;
import com.example.MontanaShop.AuthorizationTokenns.RefreshToken;
import com.example.MontanaShop.Exception.*;
import com.example.MontanaShop.Model.*;
import com.example.MontanaShop.Repository.ClientRepository;
import com.example.MontanaShop.Repository.TokenRepository;
import com.example.MontanaShop.Service.ClientManager;
import com.example.MontanaShop.Service.JwTokenManager;
import com.example.MontanaShop.Service.MailService;
import com.example.MontanaShop.Service.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/montanashop/")
public class ClientController {
    private ClientManager clientManager;
    private JwTokenManager jwTokenManager;
    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;
    private ClientRepository  clientRepository;
    private ShoppingCartManager shoppingCartManager;
    private MailService mailService;

    @Autowired
    public ClientController(ClientManager clientManager, PasswordEncoder passwordEncoder, TokenRepository tokenRepository,
                            ClientRepository clientRepository, JwTokenManager tokenManager,
    ShoppingCartManager shoppingCartManager, MailService mailService) {
        this.clientManager = clientManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.clientRepository = clientRepository;
        this.jwTokenManager = tokenManager;
        this.shoppingCartManager = shoppingCartManager;
        this.mailService = mailService;
    }


    public ClientController() {
    }
    @GetMapping("client")
    public ResponseEntity<Optional> getById(@RequestParam long id){
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<Optional>( clientManager.getById(id),httpHeaders,HttpStatus.OK);
    }
    @GetMapping("client/all")
    public HttpEntity<List<Client>> getAll(){
        return new ResponseEntity<List<Client>>(clientManager.getAll(),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("client/all-other-users")
    public HttpEntity<List<Client>> getAllByIdNot(){

        return new ResponseEntity<List<Client>>(clientManager.getAllByIdNot(clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<Token> add(@RequestBody Client client){
        HttpHeaders responseHeaders = new HttpHeaders();
        clientManager.addClient(client, passwordEncoder);
        Token token = clientManager.crreateToken(client);
        return new ResponseEntity<Token>(token, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("client/update")
    public ResponseEntity update(@RequestBody Client client){
        ResponseEntity entity = null;
        if (clientRepository.existsByUsername(client.getUsername())){
            throw new UsernameExistException(client.getUsername());
        }else if (clientRepository.existsByEmail(client.getEmail())){
            throw new EmailExsistException(client.getEmail());
        }
        else {
                Client client1 = clientManager.getById(client.getId()).get();

                if (clientManager.updateClient(client1)){
                    entity = new ResponseEntity(HttpStatus.OK);
                }else {
                    entity = new ResponseEntity(HttpStatus.NOT_FOUND);
                }
                return entity;
        }
    }
    @PutMapping("client/update-username")
    public ResponseEntity updateById(@RequestBody Client client){
        ResponseEntity entity = null;
        System.out.println(client.getUsername());
        if (clientRepository.existsByUsername(client.getUsername())){
            throw new UsernameExistException(client.getUsername());
        }else if (clientRepository.existsByEmail(client.getEmail())){
            throw new EmailExsistException(client.getEmail());
        }
        else {
        Client updateClient = clientManager.getById(client.getId()).get();
            updateClient.setUsername(client.getUsername());

            if (clientManager.updateClient(updateClient)){
                entity = new ResponseEntity(HttpStatus.OK);
            }else {
                entity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
            return entity;
    }
    @PutMapping("client/update-password")
    public ResponseEntity updateUserPassword(@RequestBody Client client){
        ResponseEntity entity = null;

        System.out.println(client.getUsername());
        Client updateClient = clientManager.getById(client.getId()).get();
        updateClient.setPassword(client.getPassword());

        if (clientManager.updateClient(updateClient)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("client/block-account")
    public ResponseEntity blockAccount(@RequestParam long id) throws MessagingException {
        ResponseEntity entity = null;
        Client client = clientManager.getById(id).get();
        client.setEnabled(false);
        if (clientManager.updateClient(client)){
            mailService.sendMail(client.getEmail(),"Blokada konta","Twoje konto zostało zblokowane z powodu nie przestrzegania regulaminu" +
                    " jeśli chcesz aby twoje konto zostało doblokowane napisz do nas ", false);
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("client/unlock-account")
    public ResponseEntity unlockAccount(@RequestParam long id) throws MessagingException {
        ResponseEntity entity = null;
        Client client = clientManager.getById(id).get();
        client.setEnabled(true);
        if (clientManager.updateClient(client)){
            mailService.sendMail(client.getEmail(),"Odblokowanie konta","Twoje konto zostało odblokowane", false);
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @DeleteMapping("client/delete/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") long id){
        ResponseEntity entity = null;
        System.out.println(clientManager.getAuthenticatedClient().getAuthorities());
        if (clientManager.deleteClient(id)){

            entity = new ResponseEntity(HttpStatus.OK);

        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @DeleteMapping("client/delete")
    public ResponseEntity deleteAccount(){
        ResponseEntity entity = null;
        if (clientManager.deleteClient(clientManager.getAuthenticatedClient().getId())){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PostMapping("confirm")
    public ResponseEntity<String> confirm(@RequestBody Confirm confirm) throws MessagingException {
        Token token = tokenRepository.findByValue(confirm.getKey());
        String confirmLink = confirm.getLink()+confirm.getKey();
        Client client = token.getClient();
        clientManager.sendToken(client, confirmLink);
        HttpHeaders responseHeaders = new HttpHeaders();
        return  new ResponseEntity<String>("", responseHeaders, HttpStatus.CREATED);
    }
    @GetMapping("activate-account/{tokenValue}")
    public ResponseEntity<Boolean> confirm(@PathVariable(name = "tokenValue")String tokenValue) throws MessagingException {
        Token token = tokenRepository.findByValue(tokenValue);
        Client client = token.getClient();
        client.setEnabled(true);
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            clientManager.updateClient(client);
            Date date = new Date(System.currentTimeMillis());
//            ShoppingCart shoppingCart = new ShoppingCart(client);
//            shoppingCartManager.add(shoppingCart);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<Boolean>(client.isEnabled(), responseHeaders, HttpStatus.CREATED);
    }
    @PostMapping("client/send-message")
    public ResponseEntity sendMessage(@RequestBody Map<String,String> message){
        ResponseEntity entity = null;
            message.forEach((e,e1)-> System.out.println(e+" "+e1));
            try {
                mailService.sendMail("montanashopapp@gmail.com","Wiadomosc od użytkownika "+clientManager.getAuthenticatedClient().getUsername()+" "+message.get("subject"),message.get("message"),false);
                entity = new ResponseEntity(HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                entity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return entity;

    }

    @PostMapping("login")
    public HashMap<String,String> login(@RequestBody Client client){
        HashMap<String,String> tokens = new  HashMap<>();
        if (clientManager.existByUserName(client.getUsername())){
            Client client1 = clientManager.findByUserName(client.getUsername());
            boolean result = passwordEncoder.matches(client.getPassword(),client1.getPassword());
            if(result){
                if (client1.isEnabled()){
                    AccesToken accesToken = jwTokenManager.createAccesToken(client1);
                    RefreshToken refreshToken = jwTokenManager.createRefreshToken(client1);
                    tokens.put("acces-token",accesToken.getToken());
                    tokens.put("refreshtoken",refreshToken.getRefreshToken());
                }
                else {
                    throw  new InactiveAccountException();
                }
            }else {
                throw new BadPasswordException();
            }
        }else {
            throw  new UsernameNotExistException();
        }
        return tokens;
    }
    @GetMapping("login-page")
    public ResponseEntity <Map<String,String>>  home(){
            Map<String, String> roleUser = new HashMap<>();
            roleUser.put("role-user", clientManager.getAuthenticatedClient().getAuthorities().toString());
            return new ResponseEntity<Map<String,String>>(roleUser, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("refreshtoken")
    public Map<String,String> refreshToken(@RequestHeader Map<String, String> headers){
        Map<String, String> tokenMap = new HashMap<>();
        jwTokenManager.setVerifyClient(jwTokenManager.verifyRefreshToken(headers.get("refreshtoken")));
        String token = jwTokenManager.createAccesToken(jwTokenManager.getVerifyClient()).getToken();
        tokenMap.put("accestoken",token);
        return tokenMap;
    }


}
