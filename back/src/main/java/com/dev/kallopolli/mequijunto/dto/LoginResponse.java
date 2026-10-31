package com.dev.kallopolli.mequijunto.dto;

public record LoginResponse(

        String token,

        String tipo,

        long expiresIn

) {
}
