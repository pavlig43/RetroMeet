package com.pavlig43.retromeetcommon

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class AppDispatcher {
    val default = Dispatchers.Default
    val io = Dispatchers.IO
    val main: MainCoroutineDispatcher = Dispatchers.Main
    val unconfined = Dispatchers.Unconfined
}
