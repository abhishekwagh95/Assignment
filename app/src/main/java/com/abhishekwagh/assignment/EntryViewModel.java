package com.abhishekwagh.assignment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.abhishekwagh.assignment.db.AppDatabase;
import com.abhishekwagh.assignment.db.Entry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class EntryViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;


    public EntryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDbInstance(application);
    }

  Flowable<List<Entry>> getList(){
        return (Flowable<List<Entry>>) appDatabase.entryDao().getAllEntries();
  }

}



