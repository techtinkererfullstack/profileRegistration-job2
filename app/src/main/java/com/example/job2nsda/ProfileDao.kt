package com.example.job2nsda

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile): Long

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM profile ORDER BY createdAt DESC")
    fun getAllProfiles(): LiveData<List<Profile>>

    @Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfileById(profileId: Int): LiveData<Profile>

    @Query("SELECT COUNT(*) FROM profile")
    fun getProfileCount(): LiveData<Int>

    @Query("DELETE FROM profile WHERE id = :profileId")
    suspend fun deleteProfileById(profileId: Int)
}