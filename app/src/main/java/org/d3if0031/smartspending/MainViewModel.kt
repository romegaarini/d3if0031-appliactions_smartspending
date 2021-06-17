package org.d3if0031.smartspending

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import android.app.Application
import org.d3if0031.smartspending.data.Record
import org.d3if0031.smartspending.data.RecordRepo


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val recordRepo = RecordRepo(application)

    fun getAllRecords(): LiveData<List<Record>>? {
        return recordRepo.getAllRecords()
    }



    fun getJumlahPengeluaran(): LiveData<Int>? {
        return recordRepo.getJumlahPengeluaran()
    }


    fun getJumlahPemasukan(): LiveData<Int>? {
        return recordRepo.getJumlahPemasukan()
    }

    fun insertRecord(record: Record) {
        recordRepo.insertRecord(record)
    }

    fun updateRecord(record: Record) {
        recordRepo.updateRecord(record)
    }

    fun deleteRecord(record: Record) {
        recordRepo.deleteRecord(record)
    }

    fun deleteAllRecord() {
        recordRepo.deleteAllRecord()
    }
    fun getAllPengeluaran(): LiveData<List<Record>>? {
        return recordRepo.getAllPengeluaran()
    }

    fun getAllPemasukan(): LiveData<List<Record>>? {
        return recordRepo.getAllPemasukan()
    }


}