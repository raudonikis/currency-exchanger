package com.raudonikis.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutinesDispatcherProvider(
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)
