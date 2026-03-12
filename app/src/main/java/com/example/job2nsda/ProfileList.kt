package com.example.job2nsda

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job2nsda.databinding.ActivityProfileListBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileList : AppCompatActivity() {
    private lateinit var binding: ActivityProfileListBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        setupRecyclerView()
        observeData()

        binding.ivAddEmployee.setOnClickListener {
            startActivity(Intent(this, AddProfile::class.java))
        }

        binding.fabLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun setupRecyclerView() {
        adapter = ProfileAdapter(
            onEditClick = { profile ->
                val intent = Intent(this, AddProfile::class.java)
                intent.putExtra("PROFILE_ID", profile.id)
                intent.putExtra("IS_EDIT", true)
                startActivity(intent)
            },
            onDeleteClick = { profile ->
                showDeleteConfirmation(profile.id, profile.name)
            },
            onItemClick = { profile ->
                val intent = Intent(this, ProfileDetails::class.java)
                intent.putExtra("PROFILE_ID", profile.id)
                startActivity(intent)
            }
        )

        binding.rvEmployees.layoutManager = LinearLayoutManager(this)
        binding.rvEmployees.adapter = adapter
    }

    private fun observeData() {
        viewModel.allProfiles.observe(this) { profiles ->
            profiles?.let {
                adapter.submitList(it)
                binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                binding.rvEmployees.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            }
        }

        viewModel.profileCount.observe(this) { count ->
            binding.tvCount.text = "Total Profiles: ${count ?: 0}"
        }
    }

    private fun showDeleteConfirmation(profileId: Int, profileName: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Profile")
            .setMessage("Are you sure you want to delete $profileName's profile?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteProfileById(profileId)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                auth.signOut()
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}