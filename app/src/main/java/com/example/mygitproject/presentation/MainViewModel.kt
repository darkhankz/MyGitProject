package com.example.mygitproject.presentation

import androidx.lifecycle.ViewModel
import com.example.mygitproject.data.ShopListRepositoryImpl
import com.example.mygitproject.domain.DeleteShopItemUseCase
import com.example.mygitproject.domain.EditShopItemUseCase
import com.example.mygitproject.domain.GetShopListUseCase
import com.example.mygitproject.domain.ShopItem

class MainViewModel: ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()


//    fun getShopList(){
//        val list = getShopListUseCase.getShopList()
//        shopList.value = list
//    }

    fun deleteShopItem(shopItem: ShopItem){
        val del = deleteShopItemUseCase.deleteShopItem(shopItem)


    }
    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)

    }

}
