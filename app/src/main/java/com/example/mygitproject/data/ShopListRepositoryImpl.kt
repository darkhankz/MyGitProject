package com.example.mygitproject.data

import com.example.mygitproject.domain.ShopItem
import com.example.mygitproject.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }

    }


    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }


    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        }?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

}