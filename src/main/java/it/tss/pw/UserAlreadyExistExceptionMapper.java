/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw;

import it.tss.pw.users.UserAlreadyExistException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author alfonso
 */
@Provider
public class UserAlreadyExistExceptionMapper implements ExceptionMapper<UserAlreadyExistException> {

    @Override
    public Response toResponse(UserAlreadyExistException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("reason", "username " + ex.getUsername() + " already exist...")
                .build();
    }

}
