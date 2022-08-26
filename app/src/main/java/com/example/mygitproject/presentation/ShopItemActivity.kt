package com.example.mygitproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mygitproject.R

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel : ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        viewModel.errorInputName.value
    }
}