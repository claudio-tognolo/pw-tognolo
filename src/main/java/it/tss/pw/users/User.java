/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.tss.pw.users;


import it.tss.pw.AbstractEntity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author claudio
 */
@NamedQueries({
    @NamedQuery(name = User.FIND_ALL, query = "select e from User e order by e.lastName"),
    @NamedQuery(name = User.FIND_BY_USR_PWD, query = "select e from User e where e.usr= :usr and e.pwd= :pwd"),
    @NamedQuery(name = User.FIND_BY_USR, query = "select e from User e where e.usr= :usr"),
    @NamedQuery(name = User.SEARCH, query = "select e from User e where e.firstName like :fname or e.lastName like :lname or e.usr like :usr")
})
@Entity
@Table(name = "user")
public class User extends AbstractEntity implements Serializable {

    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_BY_USR_PWD = "User.findByUserPwd";
    public static final String FIND_BY_USR = "User.findByUser";
    public static final String SEARCH = "User.search";

    @NotEmpty()
    @Column(name = "fname", nullable = false)
    private String firstName;

    @NotEmpty()
    @Column(name = "lname", nullable = false)
    private String lastName;
    
    @NotEmpty()
    @Column(name = "usr", nullable = false,unique = true)
    private String usr;

    @NotEmpty()
    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "birth_date")
    @JsonbDateFormat("dd/MM/yyyy")
    private LocalDate birthDate;

    public User() {
    }

    public User(String firstName, String lastName, String usr, String pwd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.usr = usr;
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    @JsonbTransient
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", usr=" + usr + ", pwd=" + pwd + ", birthDate="
                + birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + '}';
    }

}
