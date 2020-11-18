package com.example.assignment1118__2

import androidx.room.*

@Entity(tableName="users")
data class User(
    @PrimaryKey
    val uid: Int,
    val firstName: String?,
    val lastName: String?
)

@Dao
interface UserDao{
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM users WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
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