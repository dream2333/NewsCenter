package com.example.newscenter.ui.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _dialogState = MutableStateFlow(false)
    val username: StateFlow<String> = _username.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val dialogState: StateFlow<Boolean> = _dialogState.asStateFlow()
    fun onNameChange(msg: String) {
        _username.value = msg
    }

    fun onPassChange(msg: String) {
        _password.value = msg
    }

    fun changeDialogState() {
        _dialogState.value = !dialogState.value
    }
}