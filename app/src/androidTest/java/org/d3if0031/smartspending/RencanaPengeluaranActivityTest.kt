package org.d3ifcool.smartspending

import android.app.ActionBar
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.d3ifcool.smartspending.data.Record
import org.d3ifcool.smartspending.data.RecordDb
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class RencanaPengeluaranActivityTest{
    companion object{
        private val DATA_DUMMY = Record(
            0, "Beli Cilok", 10000, ""," pengeluaran"
        )
        private val DATA_DUMMY1 = Record(
            1, "THR", 10000000, ""," Pemasukan"
        )
    }
    @Before
    fun setUp() {
        // Lakukan penghapusan database setiap kali test akan dijalankan.
        InstrumentationRegistry.getInstrumentation().targetContext
            .deleteDatabase(RecordDb.DATABASE_NAME)
    }
//insertpengeluaran
    @Test
    fun testInsertPegeluaran() {
        //Jalankan RencanaPengeluaranActivity
        val activityScenario = ActivityScenario.launch(
            RencanaPengeluaranActivity::class.java
        )
        // Lakukan aksi menambah data baru pengeluaran
        onView(withId(R.id.riwayat_fab)).perform(click())
        onView(withId(R.id.judul)).perform(
            typeText(DATA_DUMMY.judul))
        onView(withId(R.id.uang)).perform(
            typeText(DATA_DUMMY.jumlah.toString()))
        onView(withId(R.id.checkbox_masukan)).perform(click())
        onView(withText(R.string.dialog_simpan)).perform(click())
        // Cek apakah hasil sesuai yang diharapkan
        onView(withText(DATA_DUMMY.judul)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY.jumlah)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY.tanggal)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY.keterangan)).check(matches(isChecked()));
        onView(withText(R.id.jumlah_pengeluaran)).check(matches(isDisplayed()))
        // Tes selesai, tutup activity nya
        activityScenario.close()
    }
//insertPemasukan
    @Test
    fun testInsertPemasukan() {
        //Jalankan RencanaPengeluaranActivity
        val activityScenario = ActivityScenario.launch(
            RencanaPengeluaranActivity::class.java
        )
        // Lakukan aksi menambah data baru pemasukan
        onView(withId(R.id.riwayat_fab)).perform(click())
        onView(withId(R.id.judul)).perform(
            typeText(DATA_DUMMY1.judul))
        onView(withId(R.id.uang)).perform(
            typeText(DATA_DUMMY1.jumlah.toString()))
        onView(withId(R.id.checkbox_masukan)).perform()
        onView(withText(R.string.dialog_simpan)).perform(click())
        // Cek apakah hasil sesuai yang diharapkan
        onView(withText(DATA_DUMMY1.judul)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY1.jumlah)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY1.tanggal)).check(matches(isDisplayed()))
        onView(withText(DATA_DUMMY1.keterangan)).check(matches(isChecked()));
        onView(withText(R.id.jumlah_pemasukan)).check(matches(isDisplayed()))
        // Tes selesai, tutup activity nya
        activityScenario.close()
    }
    //menampilkan menu saldo
    @Test
    fun ActionBar1(){
        //Jalankan RencanaPengeluaranActivity
        val activityScenario = ActivityScenario.launch(RencanaPengeluaranActivity::class.java)
        // Lakukan aksi klik menu saldo
        onView(withId(R.id.action_saldo)).perform(click())
        // lalu cek apakah action menu saldo tampil
        onView(withId(R.id.action_saldo)).check(matches(isDisplayed()))
        // Tes selesai, tutup activity nya
        activityScenario.close()
    }
    //menampilkan menu reset
    @Test
    fun ActionBar2(){
        //Jalankan RencanaPengeluaranActivity
        val activityScenario = ActivityScenario.launch(RencanaPengeluaranActivity::class.java)
        // Lakukan aksi klik menu reset
        onView(withId(R.id.action_reset)).perform(click())
        // lalu cek apakah action menu reset tampil
        onView(withId(R.id.action_reset)).check(matches(isDisplayed()))
        // Tes selesai, tutup activity nya
        activityScenario.close()
    }
}
