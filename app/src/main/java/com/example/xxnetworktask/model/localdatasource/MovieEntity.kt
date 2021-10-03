package com.example.xxnetworktask.model.localdatasource

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class MovieEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var movieId: Int? = null
    var movieName: String? = null
    var movieImage: String? = null

}

