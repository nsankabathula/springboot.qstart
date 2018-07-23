package io.java.springboot.config;

import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IController<T> {


    public ResponseEntity <List<T>> get() throws Exception;
    public ResponseEntity <T> put(T obj )throws Exception;
    public ResponseEntity <T> post(T obj )throws Exception;
    public ResponseEntity <T> delete(T  obj )throws Exception;
}
