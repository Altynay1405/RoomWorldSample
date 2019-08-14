package com.example.roomworldsample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 * You can also use a ViewModel to share data between fragments.
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;  // private member variable to hold a reference to the repository.
    private LiveData<List<Word>> mAllWords; // private LiveData member variable to cache the list of words.


    //constructor that gets a reference to the repository and gets the list of words from the repository.
    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }


   //"getter" method for all the words. This completely hides the implementation from the UI
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }
}
