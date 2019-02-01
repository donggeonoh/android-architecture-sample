package com.example.android.architecture.redprints.sampleapp.data.local.room;

import android.content.Context;

import com.example.android.architecture.redprints.sampleapp.data.local.room.dao.FavoriteDao;
import com.example.android.architecture.redprints.sampleapp.data.local.room.entity.Favorite;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }

}