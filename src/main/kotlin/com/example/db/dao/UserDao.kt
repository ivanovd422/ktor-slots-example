package com.example.db.dao

import com.example.features.registration.models.PostUserBody
import com.example.features.registration.models.PutUserBody
import com.example.features.registration.models.User
import org.jetbrains.exposed.sql.*

interface UserDao {
    fun getUserById(userId: Int): User?
    fun insertUser(postUser: PostUserBody): Int?
    fun updateUser(userId: Int, putUser: PutUserBody): User?
    fun deleteUser(userId: Int): Boolean
    fun getUserByName(usernameValue: String): User?
}

object Users : Table(), UserDao {

    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id, name = "PK_Cities_ID")

    val username = varchar("username", 255)
    val name = varchar("name", 255)
    val secondname = varchar("secondname", 255)
    val creationTime = long("creationTime")
    val password = varchar("password", 255)

    override fun getUserById(userId: Int): User? {
        return select {
            (id eq userId)
        }.mapNotNull {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun insertUser(postUser: PostUserBody): Int? {
        return (insert {
            it[name] = postUser.name
            it[secondname] = postUser.secondname
            it[username] = postUser.username
            it[password] = postUser.password
            it[creationTime] = System.currentTimeMillis()
        })[id]
    }

    override fun updateUser(userId: Int, putUser: PutUserBody): User? {
        update({ id eq userId }) { user ->
            putUser.name?.let { user[name] = it }
            putUser.secondname?.let { user[secondname] = it }
            putUser.username?.let { user[username] = it }
        }
        return getUserById(userId)
    }

    override fun deleteUser(userId: Int): Boolean {
        return deleteWhere { (id eq userId) } > 0
    }

    override fun getUserByName(usernameValue: String): User? {
        return select {
            (username eq usernameValue)
        }.mapNotNull {
            it.mapRowToUser()
        }.singleOrNull()
    }
}

fun ResultRow.mapRowToUser() =
    User(
        id = this[Users.id],
        name = this[Users.name],
        secondname = this[Users.secondname],
        username = this[Users.username],
        password = this[Users.password]
    )