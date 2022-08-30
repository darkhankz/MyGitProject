package com.example.mygitproject.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.mygitproject.R
import com.example.mygitproject.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var mViewModel : ShopItemViewModel
    private lateinit var tilName : TextInputLayout
    private lateinit var tilCount : TextInputLayout
    private lateinit var etName : EditText
    private lateinit var etCount : EditText
    private lateinit var buttonSave : Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        mViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addTextChangeClickListeners()
        launchRightMode()
        observeViewModel()

    }
    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> lunchEditMode()
            MODE_ADD -> lunchAddMode()
        }
    }
    private fun observeViewModel(){
        mViewModel.errorInputCount.observe(this){
            val massage = if (it){
                getString(R.string.error_input_count)

            } else {
                null

            }
            tilCount.error = massage
        }
        mViewModel.errorInputName.observe(this){
            val massage = if (it){
                getString(R.string.error_input_name)

            } else {
                null

            }
            tilName.error = massage
        }
        mViewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }

    private fun addTextChangeClickListeners(){
        etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }



        })
        etCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mViewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }



        })
    }

    private fun lunchEditMode() {
        mViewModel.getShopItem(shopItemId)
        mViewModel.shopItem.observe(this){
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener{
            mViewModel.editShopItem(etName.text?.toString(), etCount.text.toString())
        }

    }
    private fun lunchAddMode() {
        buttonSave.setOnClickListener{
            mViewModel.addShopItem(etName.text?.toString(), etCount.text.toString())
        }

    }


    private fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT){
            if (intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "extra_edit"
        private const val MODE_ADD = "extra_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context : Context) : Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId : Int) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }




    }
    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.save_button)
    }
}