package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.TypeDao
import vika.app.healthy_lifestyle.base.data.database.main.TypeDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultTypeMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.TypeMapper
import vika.app.healthy_lifestyle.bean.main.Type

class TypeRepository(context: Context) {
    private val typeDao: TypeDao
    private val typeDatabase: TypeDatabase = TypeDatabase.getInstance(context)
    private val typeMapper: TypeMapper

    init {
        typeDao = typeDatabase.typeDao()
        typeMapper = DefaultTypeMapper()
    }

    fun insert (type: Type) = runBlocking {
        typeDao.insert(typeMapper.toTypeEntity(type))
    }

    fun getAllByProduct():List<String>?= runBlocking{
        typeDao.getAllTypeByProduct()
    }

    fun getAllByPhys():List<String>?= runBlocking{
        typeDao.getAllTypeByPhys()
    }

    fun getByName(type: String): Type? = runBlocking{
        typeDao.getAllByName(type)?.let { typeMapper.toType(it) }
    }
}