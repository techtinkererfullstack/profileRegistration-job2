package com.example.job2nsda

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.job2nsda.databinding.ActivityProfileDetailsBinding

class ProfileDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding
    private lateinit var viewModel: ProfileViewModel
    private var profileId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        profileId = intent.getIntExtra("PROFILE_ID", 0)

        loadProfileData()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, AddProfile::class.java)
            intent.putExtra("PROFILE_ID", profileId)
            intent.putExtra("IS_EDIT", true)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun loadProfileData() {
        viewModel.getProfileById(profileId).observe(this) { profile ->
            profile?.let {
                binding.tvDetailName.setText(it.name)
                binding.tvDetailSalary.setText(it.salary.toString())
                binding.tvDetailDept.setText(it.department)
                binding.tvDetailEmail.setText(it.email)
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Profile")
            .setMessage("Are you sure you want to delete this profile?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteProfileById(profileId)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}