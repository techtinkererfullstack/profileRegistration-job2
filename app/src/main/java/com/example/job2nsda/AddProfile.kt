package com.example.job2nsda

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.job2nsda.databinding.ActivityAddProfileBinding
import com.google.android.material.snackbar.Snackbar

class AddProfile : AppCompatActivity() {
    private lateinit var binding: ActivityAddProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var isEditMode = false
    private var profileId = 0
    private var currentProfile: Profile? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        isEditMode = intent.getBooleanExtra("IS_EDIT", false)
        profileId = intent.getIntExtra("PROFILE_ID", 0)

        setupUI()


        if (isEditMode) {
            loadProfileData()
        }

        binding.btnEdit.setOnClickListener {
            saveProfile()
        }


    }

    private fun setupUI() {
        title = if (isEditMode) "Edit Profile" else "Add Profile"
        binding.btnEdit.text = if (isEditMode) "Update Profile" else "Save Profile"
    }



    private fun loadProfileData() {
        viewModel.getProfileById(profileId).observe(this) { profile ->
            profile?.let {
                currentProfile = it
                binding.etAddName.setText(it.name)
                binding.etAddEmail.setText(it.email)
                binding.etAddSalary.setText(it.salary.toString())
                binding.etAddDept.setText(it.department)

            }
        }
    }



    private fun saveProfile() {
        val name = binding.etAddName.text.toString().trim()
        val email = binding.etAddEmail.text.toString().trim()
        val salaryStr = binding.etAddSalary.text.toString().trim()
        val department =  binding.etAddDept.text.toString().trim()


        if (!validateInputs(name, email, department, salaryStr)) {
            return
        }

        val profile = Profile(
            id = if (isEditMode) profileId else 0,
            name = name,
            email = email,
            salary = salaryStr.toDouble(),
            department = department,
            createdAt = currentProfile?.createdAt ?: System.currentTimeMillis()
        )

        if (isEditMode) {
            viewModel.updateProfile(profile)
            Snackbar.make(
                findViewById(android.R.id.content),
                "Profile updated successfully",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            viewModel.insertProfile(profile)
            Snackbar.make(
                findViewById(android.R.id.content),
                "Profile saved successfully",
                Snackbar.LENGTH_LONG
            ).show()
        }

        finish()
    }

    private fun validateInputs(
        name: String, email: String, department: String,
        salary: String,
    ): Boolean {
        when {
            name.isEmpty() -> {
                binding.etAddName.error = "Name is required"
                return false
            }
            email.isEmpty() -> {
                binding.etAddEmail.error = "Email is required"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.etAddEmail.error = "Invalid email format"
                return false
            }
            department.isEmpty() -> {
                binding.etAddDept.error = "Department is required"
                return false
            }
            salary.isEmpty() -> {
                binding.etAddSalary.error = "Salary is required"
                return false
            }
            salary.toDoubleOrNull() == null -> {
                binding.etAddSalary.error = "Invalid salary format"
                return false
            }

        }
        return true
    }




}
