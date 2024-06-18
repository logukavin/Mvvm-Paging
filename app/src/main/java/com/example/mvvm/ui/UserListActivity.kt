package com.example.mvvm.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.mvvm.adapter.UserPagerAdapter
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityUserBinding
import com.example.mvvm.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListActivity : BaseActivity<ActivityUserBinding>() {
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var adapter: UserPagerAdapter

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityUserBinding =
        ActivityUserBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserPagerAdapter { item ->
            val intent = Intent(this, UserDetailsActivity::class.java)
            intent.putExtra("ID", item.id)
            startActivity(intent)
        }
        binding.recyclerview.adapter = adapter

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progressDialog.isVisible = true
            else {
                binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.getUserList().observe(this@UserListActivity) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }

    }

}