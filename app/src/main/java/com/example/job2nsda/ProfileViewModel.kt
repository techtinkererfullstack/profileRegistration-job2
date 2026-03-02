package com.example.job2nsda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProfileRepo
    val allProfiles: LiveData<List<Profile>>
    val profileCount: LiveData<Int>


    init {
        repository = ProfileRepo(AppDatabase.getDatabase(application).profileDao())
        allProfiles = repository.allProfiles
        profileCount = repository.profileCount
    }

    fun insertProfile(profile: Profile) = viewModelScope.launch {
        repository.insertProfile(profile)
    }

    fun updateProfile(profile: Profile) = viewModelScope.launch {
        repository.updateProfile(profile)
    }

    fun deleteProfile(profile: Profile) = viewModelScope.launch {
        repository.deleteProfile(profile)
    }

    fun deleteProfileById(profileId: Int) = viewModelScope.launch {
        repository.deleteProfileById(profileId)
    }

    fun getProfileById(profileId: Int): LiveData<Profile> {
        return repository.getProfileById(profileId)
    }

}