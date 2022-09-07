package com.afsal.dev.firbaseligin.ui.authimport androidx.lifecycle.ViewModelimport androidx.lifecycle.viewModelScopeimport com.afsal.dev.firbaseligin.data.AuthenticationRepositoryimport com.afsal.dev.firbaseligin.data.Resourceimport com.google.firebase.auth.FirebaseUserimport dagger.hilt.android.lifecycle.HiltViewModelimport kotlinx.coroutines.flow.MutableStateFlowimport kotlinx.coroutines.flow.StateFlowimport kotlinx.coroutines.launchimport javax.inject.Inject@HiltViewModelclass AuthViewModel @Inject constructor(private val repository: AuthenticationRepository) :ViewModel(){    private val _loginFlow= MutableStateFlow<Resource<FirebaseUser>?>(null)     val loginFlow:StateFlow<Resource<FirebaseUser>?> =_loginFlow    private val _signUpFlow= MutableStateFlow<Resource<FirebaseUser>?>(null)    val signUpFlow:StateFlow<Resource<FirebaseUser>?> =_signUpFlow    val currentUser:FirebaseUser?     get() = repository.currentUser      init {          if (repository.currentUser !=null){              _loginFlow .value= Resource.Success(repository.currentUser!!)          }      }    fun login(email:String,password:String)=viewModelScope.launch {        _loginFlow.value=Resource.Loading        val result=repository.logInUser(email,password)        _loginFlow.value=result    }    fun signUp(name:String,email:String,password:String)=viewModelScope.launch {        _signUpFlow.value=Resource.Loading        val result=repository.singUp(name,email,password)        _signUpFlow.value=result    }    fun logOut(){        repository.logOut()        _signUpFlow.value=null        _loginFlow.value=null    }}