package br.com.fiap.irrigationapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class StandardException {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
