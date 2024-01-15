package com.example.sevenwindstest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MenuModel
import com.example.domain.model.NearestLocationModel
import com.example.domain.model.Point
import com.example.domain.model.RegisterModel
import com.example.domain.usecase.GetMenuUseCase
import com.example.domain.usecase.GetMyLocationUseCase
import com.example.domain.usecase.GetNearestCoffeeShopsUseCase
import com.example.domain.usecase.UserAuthUseCase
import com.example.domain.usecase.UserRegistrationUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRegistrationUseCase: UserRegistrationUseCase,
    private val userAuthUseCase: UserAuthUseCase,
    private val getNearestCoffeeShopsUseCase: GetNearestCoffeeShopsUseCase,
    private val getMyLocationUseCase: GetMyLocationUseCase,
    private val getMenuUseCase: GetMenuUseCase
) : ViewModel() {



    private var _coffeeShopId = MutableLiveData<Int>()
    val coffeeShopId: LiveData<Int> = _coffeeShopId

    private var _locations = MutableLiveData<List<NearestLocationModel>>()
    val locations: LiveData<List<NearestLocationModel>> = _locations

    private var _myLocation = MutableLiveData<Point?>()
    val myLocation: LiveData<Point?> = _myLocation

    private var _menu = MutableLiveData<List<MenuModel>?>()
    val menu: LiveData<List<MenuModel>?> = _menu

    suspend fun getRegistration(registerModel: RegisterModel): Boolean {
        return userRegistrationUseCase.getUserRegistration(registerModel)
    }

    fun getUserAuth(registerModel: RegisterModel): Boolean {
        return userAuthUseCase.getUserAuth(registerModel)
    }

    fun getNearestCoffeeShops() {
        viewModelScope.launch {
            val list = getNearestCoffeeShopsUseCase.getNearestCoffeeShops()
            _locations.value = list
        }
    }

    fun getMyLocation() {
        viewModelScope.launch {
            val point = getMyLocationUseCase.getMyLocation()
            _myLocation.value = point
        }
    }

    fun getMenu(id: Int) {
        _coffeeShopId.value = id
        viewModelScope.launch {
            val result = getMenuUseCase.getMenu(id)
            _menu.value = result
        }
    }

    fun updateMenu(item: MenuModel) {
        val currentMenu = _menu.value?.toMutableList()
        val index = currentMenu?.indexOfFirst { it.id == item.id }
        if (index != -1 && index != null) {
            currentMenu[index] = item
        }
        _menu.value = currentMenu
    }
}