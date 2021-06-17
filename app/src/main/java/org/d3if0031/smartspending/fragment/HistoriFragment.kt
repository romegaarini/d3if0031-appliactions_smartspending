package org.d3if0031.smartspending.fragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_main.view.*
import kotlinx.android.synthetic.main.fragment_histori.histori_recycler
import org.d3if0031.smartspending.MainViewModel
import org.d3if0031.smartspending.R
import org.d3if0031.smartspending.RencanaPengeluaranActivity
import org.d3if0031.smartspending.data.Record
import org.d3ifcool.smartspending.utils.HistoriAdapter
import java.text.SimpleDateFormat
import java.util.*


class HistoriFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var adapter: HistoriAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_histori, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }
        Recycler()
        viewModel?.getAllRecords()?.observe(this, androidx.lifecycle.Observer {
            adapter?.setData(it?.toMutableList() as MutableList<Any>?)
        })
    }

    private fun Recycler() {
        adapter = HistoriAdapter(context!!, null, false) { it, it1 ->
            if (it1 == "delete") {
                (activity as RencanaPengeluaranActivity).reduceValue(it.keterangan, it.jumlah)

                viewModel?.deleteRecord(it)
                Toast.makeText(context, R.string.toast_hapus_berhasil, Toast.LENGTH_SHORT).show()
            } else {
                showAddDataDialog(it)
            }
        }
        histori_recycler.layoutManager = LinearLayoutManager(context)
        histori_recycler.setHasFixedSize(true)
        histori_recycler.adapter = adapter
    }

    @SuppressLint("SimpleDateFormat")
    private fun showAddDataDialog(record: Record) {
        val view = context?.let { AlertDialog.Builder(it) }
        val handler = layoutInflater.inflate(R.layout.dialog_main, null)

        handler.judul.setText(record.judul)
        handler.uang.setText(record.jumlah.toString())
        handler.checkbox_masukan.isEnabled = false

        view?.setView(handler)
        view?.setCancelable(true)
        view?.setPositiveButton(R.string.dialog_simpan) { _, _ ->
            val date = SimpleDateFormat(getString(R.string.date_pattern))
            val innerRecord = Record(
                record.id, handler.judul.text.toString(),
                handler.uang.text.toString().toInt(),
                date.format(Calendar.getInstance().time),
                record.keterangan
            )

            viewModel?.updateRecord(innerRecord)
        }

        view?.show()
    }
}





