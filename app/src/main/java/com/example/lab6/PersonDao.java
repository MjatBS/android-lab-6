package com.example.lab6;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PersonDao {
    @Insert
    void insert(Person person);

    @Query("SELECT COUNT(*) FROM person")
    int getQuantityPersons();

    @Query("SELECT COUNT(*) FROM person WHERE name LIKE :prefix || '%' OR surname LIKE :prefix || '%'")
    int getQuantityPersonsWithNameOrSurnameStartWith(String prefix);

}
