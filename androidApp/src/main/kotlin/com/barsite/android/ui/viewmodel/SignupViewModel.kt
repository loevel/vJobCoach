package com.barsite.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barsite.shared.di.ServiceLocator
import com.barsite.shared.domain.model.Utilisateur
import com.barsite.shared.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {
    
    private val signUpUseCase = SignUpUseCase(ServiceLocator.authRepository)
    
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()
    
    fun signup(email: String, password: String, nom: String, prenom: String) {
        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            
            signUpUseCase(email, password, nom, prenom)
                .onSuccess { user ->
                    _uiState.value = SignupUiState.Success(user)
                }
                .onFailure { error ->
                    _uiState.value = SignupUiState.Error(error.message ?: "Signup failed")
                }
        }
    }
    
    fun resetState() {
        _uiState.value = SignupUiState.Initial
    }
}

sealed class SignupUiState {
    object Initial : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val user: Utilisateur) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}
