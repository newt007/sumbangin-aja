package com.bintangpoetra.sumbanginaja.data.auth

import com.bintangpoetra.sumbanginaja.data.auth.remote.AuthService
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.di.networkModule
import com.bintangpoetra.sumbanginaja.di.preferenceModule
import com.bintangpoetra.sumbanginaja.domain.auth.mapper.toDomain
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.toBearer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class AuthDataStore(
    private val api: AuthService,
    private val pref: PreferenceManager
) : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<ApiResponse<User>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.loginUser(email, password)
            if (response.status || response.success) {
                val userData = response.data.toDomain()
                Timber.d("Here! I AM")
                pref.apply {
                    storeLoginData(userData)
                }

                /**
                 * reloadModule() is a function to unload and load network and preferences module
                 * in order to reinitialized the module
                 * */

                reloadModule()
                emit(ApiResponse.Success(userData))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun registerUser(
        name: String,
        email: String,
        password: String,
        type: String
    ): Flow<ApiResponse<String>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.registerUser(name, email, password, type)

            if (response.status) {
                val message = response.message
                emit(ApiResponse.Success(message))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun getProfileDetail(): Flow<ApiResponse<User>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getProfileDetail()

            if (response.status) {
                val userData = response.data.toDomain()
                emit(ApiResponse.Success(userData))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun updateProfile(
        name: String,
        address: String,
        phoneNumber: String
    ): Flow<ApiResponse<User>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.updateProfile(name, address, phoneNumber)

            if (response.status) {
                val userData = response.data.toDomain()
                emit(ApiResponse.Success(userData))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.toString()))
            Timber.e(ex.toString())
        }
    }

    override fun logout(): Flow<ApiResponse<String>> = flow {
        try {
            emit(ApiResponse.Loading)

            val response = api.logout()
            if (response.status) {
                pref.clearAllPreferences()
                emit(ApiResponse.Success(response.message))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (ex: Exception) {
            emit(ApiResponse.Error(ex.message.toString()))
            ex.printStackTrace()
        }
    }

    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)
        unloadKoinModules(networkModule)
        loadKoinModules(networkModule)
    }

}