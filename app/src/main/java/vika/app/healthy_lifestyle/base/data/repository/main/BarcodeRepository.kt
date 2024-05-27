package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.BarcodeDao
import vika.app.healthy_lifestyle.base.data.database.main.BarcodeDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.BarcodeMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultBarcodeMapper
import vika.app.healthy_lifestyle.bean.main.Barcode

class BarcodeRepository (context: Context) {
    private val barcodeDao: BarcodeDao
    private val barcodeDatabase: BarcodeDatabase =
        BarcodeDatabase.getInstance(context)
    private val barcodeMapper: BarcodeMapper

    init {
        barcodeDao = barcodeDatabase.barcodeDao()
        barcodeMapper = DefaultBarcodeMapper()
    }

    fun insertBarcode(barcode: Barcode) = runBlocking {
        barcodeDao.insert(barcodeMapper.toBarcodeEntity(barcode))
    }

    fun getByCode(code: String): Barcode? = runBlocking {
        barcodeDao.getByCode(code)?.let { barcodeMapper.toBarcode(it) }
    }
}