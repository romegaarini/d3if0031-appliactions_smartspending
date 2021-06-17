package org.d3if0031.smartspending.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
class Record(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "judul")
    var judul: String = "None",
    @ColumnInfo(name = "jumlah")
    var jumlah: Int = 0,
    @ColumnInfo(name = "tanggal")
    var tanggal: String = "27-02-2019",
    @ColumnInfo(name = "keterangan")
    var keterangan: String = "pengeluaran"
)


