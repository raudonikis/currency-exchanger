package com.raudonikis.currencyexchanger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.currency_exchange.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    /**
     * In reality user data would come from the backend,
     * for the purposes of this task the data is prefilled on the first run
     */
    fun initializeUser() {
        viewModelScope.launch {
            userRepository.initializeUser()
        }
    }
}
