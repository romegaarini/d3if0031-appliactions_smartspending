package org.d3ifcool.smartspending.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_rencana_pengeluaran.view.*
import org.d3ifcool.smartspending.R
import org.d3ifcool.smartspending.data.Record
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class HistoriAdapter(
    private val context: Context,
    private var datas: MutableList<Record>?,
    private val fromGraph: Boolean,
    private val clickUtils: (Record, String) -> Unit
) : RecyclerView.Adapter<HistoriAdapter.MainHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_rencana_pengeluaran, p0, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(p0: MainHolder, p1: Int) {
        if (datas != null) {
            p0.bind(datas!![p1], clickUtils)
        }
    }

    fun setData(records: MutableList<Record>?) {
        if (records == null) {
            datas?.clear()
        } else {
            datas = records
        }

        notifyDataSetChanged()
    }

    inner class MainHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var record: Record

        fun bind(record: Record, clickUtils: (Record, String) -> Unit) {
            this.record = record

            view.judul.text = record.judul
            view.uang.text = convertAndFormat(record.jumlah)
            view.tanggal.text = record.tanggal
            view.delete.setOnClickListener { clickUtils(record, "delete") }
            view.update.setOnClickListener { clickUtils(record, "edit") }

            if (record.keterangan == "pemasukan") {
                view.color.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                view.uang.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            } else {
                view.color.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
                view.uang.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            }
        }
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


