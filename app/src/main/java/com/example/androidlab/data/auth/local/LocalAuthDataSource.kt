package com.example.androidlab.data.auth.local

import com.example.androidlab.core.constants.Region
import com.example.androidlab.data.auth.local.db.UserDao
import com.example.androidlab.data.auth.local.db.UserEntity
import com.example.androidlab.data.auth.mapper.toDomain
import com.example.androidlab.domain.auth.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthDataSource @Inject constructor(
    private val userDao: UserDao
) {
    private var signedIn: User? = null

    suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        username: String,
        phone: String,
        region: Region
    ): User = withContext(Dispatchers.IO) {
        val entity = UserEntity(
            id = UUID.randomUUID().toString(),
            email = email,
            displayName = displayName,
            passwordHash = hash(password, email),
            username = username,
            phone = phone,
            region = region.name // ← enum 을 문자열로 저장
        )
        userDao.insert(entity)   // unique 위반 시 예외 발생 → 상위에서 메시지 처리
        entity.toDomain().also { signedIn = it }
    }

    suspend fun signIn(email: String, password: String): User = withContext(Dispatchers.IO) {
        val entity = userDao.findByEmail(email) ?: error("가입 정보가 없습니다.")
        if (entity.passwordHash != hash(password, email)) error("비밀번호가 일치하지 않습니다.")
        entity.toDomain().also { signedIn = it }
    }

    fun signOut() { signedIn = null }
    fun currentUser(): User? = signedIn

    private fun hash(pw: String, salt: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest("$salt:$pw".toByteArray())
            .joinToString("") { "%02x".format(it) }
}