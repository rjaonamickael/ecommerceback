package com.ecommerce.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sun.mail.smtp.SMTPAddressFailedException;

import com.ecommerce.utils.FonctionsUtiles;

@ControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	private FonctionsUtiles functions;

	// ERREUR REPONSE QUAND EMAIL EXISTANT
    @ExceptionHandler(EmailNonDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)			// CONFLICT création de ressource avec des données déjà existantes.
    public ResponseEntity<Map<String, String>> handleEmailNonDisponibleException(EmailNonDisponibleException ex) {
		
		
        return ResponseEntity.status(HttpStatus.CONFLICT)
        						.body(functions.response_error(ex.getMessage()));
    }
    
    // Erreur quand findById ne retourne aucun objet
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)			// NOT FOUND Ressource demandée introuvable.
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
    	
		
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        						.body(functions.response_error(ex.getMessage()));
    }
    
    // Erreur quand l'émail est invalide  
    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> MessagingException(MessagingException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Adresse e-mail invalide", "details", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGeneralError(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
    }
    
//    // Erreur non classifié
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)			// NOT FOUND Ressource demandée introuvable.
//    public ResponseEntity<Map<String, String>> Exception(Exception ex) {
//    	
//		
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        						.body(functions.reponse(TYPE_ERROR,ex.getMessage()));
//    }


}
