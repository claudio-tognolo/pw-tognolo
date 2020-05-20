/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.posts;

/**
 *
 * @author claudio
 */
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;


public class PostLinkAdapter implements JsonbAdapter<Post, JsonObject> {

    @Inject
    PostStore store;

    @Override
    public JsonObject adaptToJson(Post p) throws Exception {
        return Json.createObjectBuilder()
                .add("id", p.getId())
                .add("title", p.getTitle())
                .build();
    }

    @Override
    public Post adaptFromJson(JsonObject json) throws Exception {
        return store.find(Long.valueOf(json.getInt("id")));
    }

}