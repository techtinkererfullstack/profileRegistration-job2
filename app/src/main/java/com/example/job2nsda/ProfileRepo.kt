package com.example.job2nsda

import androidx.lifecycle.LiveData


class ProfileRepo(private val profileDao: ProfileDao) {

    val allProfiles: LiveData<List<Profile>> = profileDao.getAllProfiles()
    val profileCount: LiveData<Int> = profileDao.getProfileCount()

    suspend fun insertProfile(profile: Profile): Long {
        return profileDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    fun getAllProfiles(): LiveData<List<Profile>> {
        return profileDao.getAllProfiles()
    }
    fun getProfileById(profileId: Int): LiveData<Profile> {
        return profileDao.getProfileById(profileId)
    }

    fun getProfileCount(): LiveData<Int> {
        return profileDao.getProfileCount()
    }

    suspend fun deleteProfileById(profileId: Int) {
        profileDao.deleteProfileById(profileId)
    }
}