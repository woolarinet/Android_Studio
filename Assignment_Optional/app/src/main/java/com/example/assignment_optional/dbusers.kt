package com.example.assignment_optional

import androidx.room.*

@Entity(tableName="users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val username: String?,
    val password: String?
)

@Dao
interface UserDao{
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM users WHERE username LIKE :first AND " +
            "password LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun delete(use: User)
}
@Database(entities = arrayOf(User::class), version=1)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}