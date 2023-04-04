package com.lmy.wanandroid.util

import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lmy.BaseApplication

/**
 * 数据存储
 * DataStore优势：
 * 1、DataStore基于事务方式处理数据更新。
 * 2、DataStore基于Kotlin Flow存取数据，默认在Dispatchers.IO里异步操作，避免阻塞UI线程，且在读取数据时能对发生的Exception进行处理。
 * 3、不提供apply()、commit()存留数据的方法。
 * 4、支持SP一次性自动迁移至DataStore中。
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "findDevice")

val globalDataStore: DataStore<Preferences> = BaseApplication.mContext.dataStore

/**
 * 数据存储工具类
 */
object DataStoreUtil {
    /**
     *
     * 异步获取数据
     * */
    @Suppress("UNCHECKED_CAST")
    fun <Value> getData(key: String, defaultValue: Value): Value {
        val result = when (defaultValue) {
            is Int -> readIntData(buildKey(key), defaultValue)
            is Float -> readFloatData(buildKey(key), defaultValue)
            is Double -> readDoubleData(buildKey(key), defaultValue)
            is Boolean -> readBoolean(buildKey(key), defaultValue)
            is String -> readString(buildKey(key), defaultValue)
            is Long -> readLong(buildKey(key), defaultValue)
            else -> throw IllegalArgumentException("can not find the $key type")
        }
        return result as Value
    }
    
    /**
     * 同步获取数据
     * */
    @Suppress("UNCHECKED_CAST")
    fun <Value> getSyncData(key: String, defaultValue: Value): Flow<Value> {
        val result = when (defaultValue) {
            is Int -> readSyncIntData(buildKey(key), defaultValue)
            is Long -> readSyncLongData(buildKey(key), defaultValue)
            is Float -> readSyncFloatData(buildKey(key), defaultValue)
            is Double -> readSyncDoubleData(buildKey(key), defaultValue)
            is Boolean -> readSyncBooleanData(buildKey(key), defaultValue)
            is String -> readSyncStringData(buildKey(key), defaultValue)
            else -> throw IllegalArgumentException("can Not find the $key type")
        }
        return result as Flow<Value>
    }
    
    /**
     * 异步输入数据
     */
    fun <Value> putData(key: String, value: Value) {
        when (value) {
            is Int -> saveIntData(buildKey(key), value)
            is Long -> saveLongData(buildKey(key), value)
            is Float -> saveFloatData(buildKey(key), value)
            is Double -> saveDoubleData(buildKey(key), value)
            is Boolean -> saveBoolean(buildKey(key), value)
            is String -> saveString(buildKey(key), value)
            else -> throw IllegalArgumentException("unSupport $value type !!!")
        }
    }
    
    /**
     * 同步输入数据
     */
    suspend fun <Value> putSyncData(key: String, value: Value) {
        when (value) {
            is Int -> saveSyncIntData(buildKey(key), value)
            is Long -> saveSyncLongData(buildKey(key), value)
            is Float -> saveSyncFloatData(buildKey(key), value)
            is Double -> saveSyncDoubleData(buildKey(key), value)
            is Boolean -> saveSyncBoolean(buildKey(key), value)
            is String -> saveSyncString(buildKey(key), value)
            else -> throw IllegalArgumentException("unSupport $value type !!!")
        }
    }
    
    private fun saveString(key: String, value: String) = GlobalScope.launch {
        saveSyncString(key, value)
    }
    
    private suspend fun saveSyncString(key: String, value: String) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[stringPreferencesKey(key)] = value
        }
    }
    
    private fun saveBoolean(key: String, value: Boolean) =
        GlobalScope.launch { saveSyncBoolean(key, value) }
    
    private suspend fun saveSyncBoolean(key: String, value: Boolean) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[booleanPreferencesKey(key)] = value
        }
    }
    
    private fun saveDoubleData(key: String, value: Double) = GlobalScope.launch {
        saveSyncDoubleData(key, value)
    }
    
    private suspend fun saveSyncDoubleData(key: String, value: Double) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[doublePreferencesKey(key)] = value
        }
    }
    
    private fun saveFloatData(key: String, value: Float) =
        GlobalScope.launch { saveSyncFloatData(key, value) }
    
    private suspend fun saveSyncFloatData(key: String, value: Float) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[floatPreferencesKey(key)] = value
        }
    }
    
    private fun saveLongData(key: String, value: Long) =
        GlobalScope.launch { saveSyncLongData(key, value) }
    
    private suspend fun saveSyncLongData(key: String, value: Long) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[longPreferencesKey(key)] = value
        }
    }
    
    private fun saveIntData(key: String, value: Int) =
        GlobalScope.launch { saveSyncIntData(key, value) }
    
    private suspend fun saveSyncIntData(key: String, value: Int) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[intPreferencesKey(key)] = value
        }
    }
    
    private fun readSyncStringData(key: String, defaultValue: String): Flow<String> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[stringPreferencesKey(key)] ?: defaultValue }
    
    private fun readSyncBooleanData(key: String, defaultValue: Boolean): Flow<Boolean> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[booleanPreferencesKey(key)] ?: defaultValue }
    
    private fun readSyncDoubleData(key: String, defaultValue: Double): Flow<Double> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[doublePreferencesKey(key)] ?: defaultValue }
    
    private fun readSyncFloatData(key: String, defaultValue: Float): Flow<Float> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[floatPreferencesKey(key)] ?: defaultValue }
    
    private fun readSyncLongData(key: String, defaultValue: Long): Flow<Long> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[longPreferencesKey(key)] ?: defaultValue }
    
    private fun readSyncIntData(key: String, defaultValue: Int): Flow<Int> =
        globalDataStore.data.catch {
            checkCollectorAction(it, this)
        }.map { it[intPreferencesKey(key)] ?: defaultValue }
    
    private fun readIntData(key: String, defaultValue: Int): Int {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[intPreferencesKey(key)] ?: resultValue
                true
            }
        }
        return resultValue
    }
    
    private fun readFloatData(key: String, defaultValue: Float): Float {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[floatPreferencesKey(key)] ?: resultValue
                true
            }
        }
        return resultValue
    }
    
    private fun readDoubleData(key: String, defaultValue: Double): Double {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[doublePreferencesKey(key)] ?: resultValue
                true
            }
        }
        return resultValue
    }
    
    private fun readBoolean(key: String, defaultValue: Boolean): Boolean {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[booleanPreferencesKey(key)] ?: resultValue
                true
            }
        }
        return resultValue
    }
    
    private fun readString(key: String, defaultValue: String): String {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[stringPreferencesKey(key)] ?: defaultValue
                
                true
            }
        }
        return resultValue
    }
    
    private fun readLong(key: String, defaultValue: Long): Long {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[longPreferencesKey(key)] ?: resultValue
                true
            }
        }
        return resultValue
    }
    
    /**
     * 读取set
     */
    fun readSetString(key: String, defaultValue: Set<String> = HashSet()): Set<String> {
        var resultValue = defaultValue
        runBlocking {
            globalDataStore.data.first {
                resultValue = it[stringSetPreferencesKey(key)] ?: defaultValue
                true
            }
        }
        
        return resultValue
    }
    
    /**
     * 读取set
     */
    fun readSyncSetString(key: String, defaultValue: Set<String> = HashSet()): Flow<Set<String>> =
        globalDataStore.data.catch { e ->
            checkCollectorAction(e, this)
        }.map { it[stringSetPreferencesKey(key)] ?: defaultValue }
    
    /**
     * 写入set
     */
    fun writeSetString(key: String, value: Set<String>) = runBlocking {
        writeSyncSetString(key, value)
    }
    
    /**
     * 写入set
     */
    suspend fun writeSyncSetString(key: String, value: Set<String>) {
        globalDataStore.edit { mutablePreferences ->
            mutablePreferences[stringSetPreferencesKey(key)] = value
        }
    }
    
    /**
     * 清除数据
     */
    suspend fun clear() {
        globalDataStore.edit { it.clear() }
    }
    
    /**
     * 清除数据
     */
    fun clearSync() {
        GlobalScope.launch {
            clear()
        }
    }
    
    private suspend fun checkCollectorAction(e: Throwable, collector: FlowCollector<Preferences>) {
        if (e is IOException) {
            e.printStackTrace()
            collector.emit(emptyPreferences())
        } else {
            throw  e
        }
    }
    
    /**
     * 构造存储key
     */
    fun buildKey(key: String): String {
        return key
        // return "${AccountUtil.instance.getAccountUserId()}_$key"
    }
}
