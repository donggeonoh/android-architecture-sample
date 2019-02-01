package com.example.android.architecture.redprints.sampleapp.data.local.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
@Entity(tableName = "favorites")
public class Favorite {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final String id;

    public Favorite(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }
}
