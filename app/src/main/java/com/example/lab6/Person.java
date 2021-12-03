package com.example.lab6;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity(indices = {@Index("name"), @Index(value = {"name", "surname"})})
public class Person {
    @PrimaryKey
    @NotNull
    public UUID id;

    public String name;
    public String surname;
    public String comment;

    public Person(String name, String surname, String comment){
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }


}
