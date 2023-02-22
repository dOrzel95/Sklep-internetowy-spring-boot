package com.example.MontanaShop.Service;


import com.example.MontanaShop.Exception.*;
import com.example.MontanaShop.Model.Client;
import com.example.MontanaShop.Model.Token;
import com.example.MontanaShop.Repository.ClientRepository;
import com.example.MontanaShop.Repository.TokenRepository;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@Service
public class ClientManager implements UserDetailsService{
    private ClientRepository clientRepository;
    private TokenRepository tokenRepository;
    private MailService mailService;

    @Autowired
    public ClientManager(ClientRepository clientRepository, TokenRepository tokenRepository, MailService mailService) {
        this.clientRepository = clientRepository;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }
    public ClientManager() {


    }

    public Optional<Client> getById(long id){
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }
    public List<Client> getAll(){
        if (clientRepository.findAll().isEmpty()) {
            throw new NoResultsException();
        }
        return clientRepository.findAll();
    }
    public List<Client> getAllByIdNot(long id){
        return clientRepository.findAllByIdNot(id);
    }
    public boolean addClient(Client client, PasswordEncoder passwordEncoder){
        boolean add=false;
        if (clientRepository.existsByUsername(client.getUsername())){
            throw new UsernameExistException(client.getUsername());
        }else if (clientRepository.existsByEmail(client.getEmail())){
            throw new EmailExsistException(client.getEmail());
        }
        else {
            try {
                client.setRole("ROLE_USER");
                client.setPassword(passwordEncoder.encode(client.getPassword()));
                clientRepository.save(client);
                add = true;
            }
            catch (Exception e){
                throw  new FailedToAddResource();
            }
        }

        return add;                                                                                                                                                                                                  }
    public boolean updateClient(@RequestBody Client client){
        boolean update=false;
        try {
            clientRepository.save(client);
            update = true;
        }
        catch (Exception e){
            throw  new FailedToAddResource();
        }
        return update;
    }
    public boolean deleteClient(@RequestParam long id){
        boolean delete=false;
        try {
            clientRepository.deleteById(id);
            delete = true;
        }
        catch (Exception e){
            delete = false;
            e.printStackTrace();
        }
        return delete;
    }
    public Token crreateToken(Client client){
            String tokenValue = UUID.randomUUID().toString();
            Token token = new Token();
            token.setValue(tokenValue);
            token.setClient(client);
            tokenRepository.save(token);
            return token;
    }
    public boolean existByUserName(String username) {
        return clientRepository.existsByUsername(username);
    }
    public boolean existById(long id) {
        return clientRepository.existsById(id);
    }
    public Client findByUserName (String username) {
        return clientRepository.findByUsername(username);
    }
        public void sendToken(Client client, String comfirmLink) throws MessagingException {
        mailService.sendMail(client.getEmail(),"Aktywacja konta", comfirmLink, false);
    }
    public Client getAuthenticatedClient ()
    {
        Client client = clientRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return client;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return clientRepository.findByUsername(s);
    }
}
