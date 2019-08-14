package com.example.roomworldsample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * The data for this app is words, and each word is an Entity. Create a class called Word that
 * describes a word Entity. You need a constructor and a "getter" method for the data model class,
 * because that's how Room knows to instantiate your objects.
 */


@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word (String word) {

        this.mWord = word;
    }

    public String getWord(){
        return this.mWord;
    }
}
