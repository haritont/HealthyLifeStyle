package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.BarcodeEntity
import vika.app.healthy_lifestyle.bean.main.Barcode


interface BarcodeMapper {
    fun toBarcodeEntity(barcode: Barcode): BarcodeEntity
    fun toBarcode(barcodeEntity: BarcodeEntity): Barcode
}

class DefaultBarcodeMapper: BarcodeMapper{
    override fun toBarcodeEntity(barcode: Barcode): BarcodeEntity {
        return BarcodeEntity(
            id = barcode.id,
            code = barcode.code,
            idIngredient = barcode.idIngredient
        )
    }

    override fun toBarcode(barcodeEntity: BarcodeEntity): Barcode {
        return Barcode(
            id = barcodeEntity.id,
            code = barcodeEntity.code,
            idIngredient = barcodeEntity.idIngredient
        )
    }
}