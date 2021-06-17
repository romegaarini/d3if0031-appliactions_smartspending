package org.d3if0031.smartspending

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_rencana_pengeluaran.*
import kotlinx.android.synthetic.main.dialog_main.view.*
import kotlinx.android.synthetic.main.dialog_tabungan.view.*
import org.d3if0031.smartspending.data.Record

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class RencanaPengeluaranActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PagerAdapter

    private var jumlahPengeluaran = 0
    private var jumlahPemasukan = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rencana_pengeluaran)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        if (savedInstanceState != null) {
            jumlahPemasukan = savedInstanceState.getInt("jumlahPemasukan")
            jumlahPengeluaran = savedInstanceState.getInt("jumlahPengeluaran")
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getJumlahPengeluaran()?.observe(this, Observer {
            if (it != null) {
                jumlah_pengeluaran.text =convertAndFormat(it)
                jumlahPengeluaran = it
            } else {
                jumlah_pengeluaran.text =convertAndFormat(0)
            }
        })

        viewModel.getJumlahPemasukan()?.observe(this, Observer {
            if (it != null) {
                jumlah_pemasukan.text =convertAndFormat(it)
                jumlahPemasukan = it
            } else {
                jumlah_pemasukan.text = convertAndFormat(0)
            }
        })


        adapter = org.d3ifcool.smartspending.utils.PagerAdapter(supportFragmentManager)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 2
        layout.setupWithViewPager(view_pager)
        riwayat_fab.setOnClickListener { showAddDataDialog() }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt("jumlahPemasukan", jumlahPemasukan)
        outState?.putInt("jumlahPengeluaran", jumlahPengeluaran)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.action_reset -> showDialog()
            item.itemId == R.id.action_saldo -> showSaldoDialog()
        }

        return true
    }

    fun reduceValue(key: String, amount: Int) {

        when (key) {
            "pemasukan" -> jumlahPemasukan -= amount
            "pengeluaran" -> jumlahPengeluaran -= amount
        }
    }

    private fun showSaldoDialog() {
        val view = AlertDialog.Builder(this)
        val handler = layoutInflater.inflate(R.layout.dialog_tabungan, null)

        handler.pemasukan.text = convertAndFormat(jumlahPemasukan)
        handler.pengeluaran.text = convertAndFormat(jumlahPengeluaran)

        handler.saldo.text =
            convertAndFormat(jumlahPemasukan - jumlahPengeluaran)
        view.setView(handler)
        view.setTitle(R.string.dialog_title_saldo)
        view.setCancelable(true)
        view.setPositiveButton("Close", null)
        view.show()
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
//        dialog.setTitle(getString(R.string.perhatian))
        dialog.setMessage(R.string.dialog_message)
        dialog.setCancelable(true)
        dialog.setPositiveButton("Ya") { _, _ ->
            viewModel.deleteAllRecord()
            jumlahPemasukan = 0
            jumlahPengeluaran = 0

        }
        dialog.setNegativeButton("Tidak") { innerDialog, _ ->
            innerDialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showAddDataDialog() {
        val dialog = this.let { AlertDialog.Builder(it) }
        val dialogView = layoutInflater.inflate(R.layout.dialog_main, null)

        dialog.setView(dialogView)
        dialog.setTitle(R.string.rencana_harian)
        dialog.setCancelable(true)
        dialog.setPositiveButton(R.string.dialog_simpan) { innerDialog, _ ->
            val isPemasukan = if (dialogView.checkbox_masukan.isChecked) {
                "pemasukan"
            } else {
                "pengeluaran"
            }

            if (!dialogView.judul.text.isBlank() && !dialogView.uang.text.isBlank()) {
                val jumlahPemasukan = dialogView.uang.text.toString()
                val date = SimpleDateFormat(getString(R.string.date_pattern))
                    val record = Record(
                        0,
                        dialogView.judul.text.toString(),
                        jumlahPemasukan.toInt(),
                        date.format(Calendar.getInstance().time),
                        isPemasukan
                    )

                    viewModel.insertRecord(record)
                innerDialog.dismiss()
            } else {
                Toast.makeText(this, R.string.toast_isi_kolom, Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Batal") { innerDialog, _ ->
            innerDialog.dismiss()
        }

        dialog.show()
    }
    private fun convertAndFormat(s: Int): CharSequence? {
        val format = DecimalFormat.getCurrencyInstance() as DecimalFormat
        val formatRp = DecimalFormatSymbols()
        formatRp.currencySymbol = "Rp."
        formatRp.monetaryDecimalSeparator = ','
        formatRp.groupingSeparator = '.'

        format.decimalFormatSymbols = formatRp
        return format.format(s.toLong())
    }
}
