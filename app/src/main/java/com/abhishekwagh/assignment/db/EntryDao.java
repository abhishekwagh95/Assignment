package com.abhishekwagh.assignment.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Query(("SELECT * FROM entry"))
    List<Entry> getAllEntries();

    @Query("SELECT * FROM entry WHERE uid=:id")
    Entry getEntry(long id);

    @Insert
    void insertEntry(Entry entry);


    @Delete
    void delete(Entry entry);

    @Update
 void updateEntry(Entry entry);
}
