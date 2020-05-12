/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pw.security;

/**
 *
 * @author claudio
 */
import java.io.Serializable;
import java.util.Objects;


public class Credential implements Serializable {

    private String usr;
    private String pwd;

    public Credential() {
    }

    public Credential(String usr, String pwd) {
        this.usr = usr;
        this.pwd = pwd;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.usr);
        hash = 37 * hash + Objects.hashCode(this.pwd);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Credential other = (Credential) obj;
        if (!Objects.equals(this.usr, other.usr)) {
            return false;
        }
        return Objects.equals(this.pwd, other.pwd);
    }

    @Override
    public String toString() {
        return "Credential{" + "usr=" + usr + ", pwd=" + pwd + '}';
    }
}

