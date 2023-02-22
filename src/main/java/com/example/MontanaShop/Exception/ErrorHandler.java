package com.example.MontanaShop.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;


@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleWebExeption(RuntimeException e, WebRequest webRequest) {

        return handleExceptionInternal(e,new ResponseEntity<String>(e.getMessage(), HttpHeaders.EMPTY,HttpStatus.NOT_FOUND),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND, webRequest);
    }
    @ResponseBody
    @ExceptionHandler(NoResultsException.class)
    public ResponseEntity<Object> handleExeption(RuntimeException e, WebRequest webRequest) {
        return handleExceptionInternal(e,new ResponseEntity<String>(e.getMessage(), HttpHeaders.EMPTY,HttpStatus.NOT_FOUND),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND, webRequest);
    }
    @ResponseBody
    @ExceptionHandler(UpdateNotFoundException.class)
    public ResponseEntity<Object> handleUpdateExeption(RuntimeException e, WebRequest webRequest) {
        return handleExceptionInternal(e,new ResponseEntity<String>(e.getMessage(), HttpHeaders.EMPTY,HttpStatus.NOT_FOUND),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND, webRequest);
    }
    @ResponseBody
    @ExceptionHandler(FailedToAddResource.class)
    public ResponseEntity<Object> handleAddResourceException(RuntimeException e, WebRequest webRequest){
        System.err.println(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, new ResponseEntity<String>(e.getMessage(), HttpHeaders.EMPTY,HttpStatus.NOT_FOUND),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND, webRequest);
    }

    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handleTokenException(RuntimeException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return handleExceptionInternal(e, new ResponseEntity<String>(e.getMessage(), HttpHeaders.EMPTY,HttpStatus.NOT_FOUND),HttpHeaders.EMPTY,HttpStatus.NOT_FOUND, webRequest);
    }

    @ResponseBody
    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<UsernameExistException> handleExistsByUsernameException(UsernameExistException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<UsernameExistException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
    @ResponseBody
    @ExceptionHandler(EmailExsistException.class)
    public ResponseEntity<EmailExsistException> handleExistEmailException(EmailExsistException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<EmailExsistException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotExistException.class)
    public ResponseEntity<UsernameNotExistException> handleNotExistLoginException(UsernameNotExistException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<UsernameNotExistException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
    @ResponseBody
    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<InactiveAccountException> handleInactiveAccountException(InactiveAccountException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<InactiveAccountException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BadPasswordException.class)
    public ResponseEntity<BadPasswordException> handleBadPasswordException(BadPasswordException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<BadPasswordException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
    @ResponseBody
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<RefreshTokenExpiredException> handleRefreshTokenExpiredException(RefreshTokenExpiredException e, WebRequest webRequest) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<RefreshTokenExpiredException>(e,new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }
    @ResponseBody
    @ExceptionHandler(ExistShoppingCartException.class)
    public ResponseEntity<ExistShoppingCartException> handleRefreshTokenExpiredException(ExistShoppingCartException e, WebRequest webRequest) {
        return new ResponseEntity<ExistShoppingCartException>(e,new HttpHeaders(),HttpStatus.NOT_FOUND);
    }


};
