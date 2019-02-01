package com.example.android.architecture.redprints.sampleapp.data.local.room.dao;

import com.example.android.architecture.redprints.sampleapp.data.local.room.entity.Favorite;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(final Favorite favorite);

    @Query("SELECT * FROM favorites")
    Single<List<Favorite>> selectAll();

    @Query("SELECT * FROM favorites WHERE id = :id")
    Single<Favorite> selectById(final String id);

    @Delete
    Completable delete(final Favorite favorite);
}
