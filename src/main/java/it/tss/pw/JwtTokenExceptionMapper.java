/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw;

/**
 *
 * @author claudio
 */
import it.tss.pw.security.JwtTokenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author alfonso
 */
public class JwtTokenExceptionMapper implements ExceptionMapper<JwtTokenException> {

    @Override
    public Response toResponse(JwtTokenException ex) {
         return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("reason", ex.getMessage())
                .build();
    }
    
}