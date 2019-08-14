package com.example.roomworldsample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Room uses the DAO to issue queries to its database.
 * --------------------------------------------------------------
 * By default, to avoid poor UI performance, Room doesn't allow you to issue database queries
 * on the main thread. LiveData applies this rule by automatically running the query asynchronously
 * on a background thread, when needed.
 * -----------------------------------------------------------------
 * Room provides compile-time checks of SQLite statements.
 * ---------------------------------------------------------------------
 * Your Room class must be abstract and extend RoomDatabase.
 * --------------------------------------------------------------------
 * Usually, you only need one instance of the Room database for the whole app.
 */

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

   /* Make the WordRoomDatabase a singleton to prevent having multiple instances of
    the database opened at the same time.*/
   //// marking the instance as volatile to ensure atomic access to the variable
    private static volatile WordRoomDatabase INSTANCE;

    static WordRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE == null){
                    //Create a database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class,"word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
            };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

            Word word = new Word("Salem");
            mDao.insert(word);

            word= new Word("Kalaysin?");
            mDao.insert(word);

            return null;
        }
    }
}
