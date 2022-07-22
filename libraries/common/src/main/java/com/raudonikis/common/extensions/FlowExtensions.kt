package com.raudonikis.common.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

fun <T> Flow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner) = with(lifecycleOwner) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            try {
                this@launchWhenStarted.collect()
            } catch (t: Throwable) {
                Timber.tag(javaClass.simpleName).e(t, t.localizedMessage)
            }
        }
    }
}
