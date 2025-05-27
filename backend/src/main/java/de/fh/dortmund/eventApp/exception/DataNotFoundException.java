package de.fh.dortmund.eventApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DataNotFoundException extends ResponseStatusException {
    public DataNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Data not found");
    }
}
