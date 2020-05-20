/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.documents;

/**
 *
 * @author claudio
 */
import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;


public class DocumentUploadForm {
    
    private byte[] fileData;
    private String fileName;
    private String mediaType;
    
    public String getFileName() {
        return fileName;
    }
 
    @FormParam("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public byte[] getFileData() {
        return fileData;
    }
 
    @FormParam("uploadedFile")
    @PartType("application/octet-stream")
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getMediaType() {
        return mediaType;
    }

    @FormParam("mediaType")
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
}
