package ru.blackmirrror.todoapp.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import ru.blackmirrror.todoapp.data.utils.UiState

/**
 * @brief реализация репозитория пользователя
 */

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
): AuthRepository {

    override fun loginUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Успешная авторизация!"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Проверьте email и пароль"))
            }
    }

    override fun registerUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    result.invoke(
                        UiState.Success("Регистрация прошла успешно!")
                    )
                }else{
                    try {
                        throw it.exception ?: java.lang.Exception("Ошибка авторизации")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Пароль должен содержать не менее 6 символов"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Некорректный email"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Этот email уже зарегистрирован"))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message ?: "Ошибка"))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage ?: "Ошибка"
                    )
                )
            }
    }

    override fun getCurrentUserUid(): String? {
        val user = auth.currentUser
        return user?.uid
    }

    override fun logoutUser() {
        auth.signOut()
    }
}