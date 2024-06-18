package com.example.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityUserDetailsBinding
import com.example.mvvm.extensions.NetworkResult
import com.example.mvvm.extensions.loadUrl
import com.example.mvvm.viewmodel.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsActivity : BaseActivity<ActivityUserDetailsBinding>() {
    private val viewModel: UserDetailsViewModel by viewModels()
    private var id: Int = 0

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityUserDetailsBinding =
        ActivityUserDetailsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("ID", 0)
        this.id = id
        lifecycleScope.launch {
            viewModel.getUserDetails(id)
        }

        viewModel.userDetailsResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressDialog.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressDialog.isVisible = false
                }

                is NetworkResult.Success -> {
                    binding.tvName.text = it.data.firstName
                    binding.tvLastName.text = it.data.lastName
                    binding.tvEmail.text = it.data.email
                    binding.imgUser.loadUrl(it.data.image)
                    binding.progressDialog.isVisible = false
                }
            }
        }
    }
    }
