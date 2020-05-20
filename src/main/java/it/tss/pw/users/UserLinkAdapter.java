/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.users;

/**
 *
 * @author claudio
 */
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author alfonso
 */
public class UserLinkAdapter implements JsonbAdapter<User, JsonObject> {

    @Inject
    UserStore store;

    @Override
    public JsonObject adaptToJson(User user) throws Exception {
        return Json.createObjectBuilder()
                .add("id", user.getId())
                .add("username", user.getUsr())
                .build();
    }

    @Override
    public User adaptFromJson(JsonObject json) throws Exception {
        return store.find(Long.valueOf(json.getInt("id"))).orElseThrow(() -> new NotFoundException());
    }

}