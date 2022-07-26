package com.raudonikis.currency_exchange.repo

import android.content.SharedPreferences
import androidx.core.content.edit
import com.raudonikis.currency_exchange.balances.CurrencyBalancesRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val preferences: SharedPreferences,
    private val balancesRepository: CurrencyBalancesRepository
) {

    var isUserInitialized: Boolean
        get() = preferences.getBoolean(KEY_IS_USER_INITIALIZED, false)
        set(value) {
            preferences.edit(commit = true) {
                putBoolean(KEY_IS_USER_INITIALIZED, value)
            }
        }

    suspend fun initializeUser() {
        if (isUserInitialized) {
            return
        }
        balancesRepository.initializeBaseCurrency()
        isUserInitialized = true
    }

    companion object {
        private const val KEY_IS_USER_INITIALIZED = "is_user_initialized"
    }
}
