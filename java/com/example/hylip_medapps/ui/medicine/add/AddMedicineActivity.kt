package com.windranger.reminder.ui.medicine.add

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.model.alarm.Times
import com.windranger.reminder.model.db.CONSUMED_WITH
import com.windranger.reminder.model.drug.Drug
import com.windranger.reminder.model.drug.DrugType
import com.windranger.reminder.ui.medicine.ClockAdapter
import com.windranger.reminder.util.ext.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_add_medicine.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AddMedicineActivity : ToolbarActivity(), AddMedicineView {

    @Inject lateinit var presenter: AddMedicinePresenter

    private val clockAdapter by lazy { ClockAdapter { openTimeDialog(it) } }

    private val calendar by lazy { Calendar.getInstance() }
    private lateinit var timeDialog: TimePickerDialog
    private var position = 0

    private val typeDef = "Pilih Golongan Obat"
    private val drugDef = "Pilih Nama Obat"

    private val typeStr = mutableListOf(typeDef)
    private val drugStr = mutableListOf(drugDef)

    private lateinit var typeAdapter: ArrayAdapter<String>
    private lateinit var drugAdapter: ArrayAdapter<String>

    private lateinit var typeList: List<DrugType>
    private lateinit var drugList: List<Drug>

    private var selectedType = 0
    private var selectedDrug = 0

    private var consumed = ""
    private var consumedTime = ""
    private var dosis = ""

    private var id = 0
    private var typeId = 0
    private var drug = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        btnSave.setOnClickListener { submitForm() }

        consumed = radio_before.text.toString()
        consumedTime = radio_15m.text.toString()
        dosis = radio_1times.text.toString()

        initDosis()
        initSpinGolongan()
        initSpinObat()
        initTimeDialog()

        val data = intent.getParcelableExtra<Alarm>(EXTRAS_DATA)
        data?.let { initData(it) }

        presenter.attachView(this)
        presenter.getDrugType()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun updateDrugType(list: List<DrugType>) {
        typeList = list
        typeList.mapTo(typeStr) { it.name ?: "" }
        typeAdapter.notifyDataSetChanged()

        if (typeId != 0) {
            val str = typeList.find { it.id == typeId }
            spinGolongan.setSelection(typeAdapter.getPosition(str?.name))
        }
    }

    override fun updateDrug(list: List<Drug>) {
        drugList = list
        drugStr.clear()
        drugStr.add(drugDef)
        drugList.mapTo(drugStr) { it.name ?: "" }

        drugAdapter.notifyDataSetChanged()

        if (drug.isNotEmpty()) {
            Timber.d("Drug: $drug")
            val pos = drugAdapter.getPosition(drug)
            Timber.d("Drug: $pos")
            spinObat.setSelection(pos)
            drug = ""
        } else spinObat.setSelection(0)
    }

    override fun closePage() {
        setOk()
    }

    override fun showLoading() {
        showLoadingBar()
    }

    override fun hideLoading() {
        hideLoadingBar()
    }

    override fun showMessage(message: String) {
        showPopup(message)
    }

    fun onEat(view: View) {
        consumed = (view as RadioButton).text.toString()
        when (view.id) {
            R.id.radio_before, R.id.radio_after -> {
                groupMinute.visible()
                tvWhenEat.gone()
            }
            R.id.radio_when -> {
                consumed = CONSUMED_WITH
                consumedTime = "Diminum setelah suapan nasi pertama"
                groupMinute.gone()
                tvWhenEat.visible()
            }
        }
    }

    fun onTimeChecked(view: View) {
        consumedTime = (view as RadioButton).text.toString()
    }

    fun onDosage(view: View) {
        dosis = (view as RadioButton).text.toString()
        when (view.id) {
            R.id.radio_1times -> clockAdapter.updateList(listOf("08:00"))
            R.id.radio_2times -> clockAdapter.updateList(listOf("08:00", "09:00"))
            R.id.radio_3times -> clockAdapter.updateList(listOf("08:00", "09:00", "10:00"))
            R.id.radio_4times -> clockAdapter.updateList(listOf("08:00", "09:00", "10:00", "11:00"))
        }
    }

    private fun initDosis() {
        rvClock.layoutManager = GridLayoutManager(this, 4)
        rvClock.adapter = clockAdapter
        clockAdapter.updateList(listOf("08:00"))

        groupMinute.visible()
        tvWhenEat.gone()
    }

    private fun initTimeDialog() {
        timeDialog = TimePickerDialog.newInstance(
                { _, hourOfDay, minute, second ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, second)

                    val time = dateFormatter(calendar.timeInMillis, TIME_FORMAT)
                    clockAdapter.updateItem(time, position)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                true
        )
    }

    private fun openTimeDialog(pos: Int) {
        position = pos
        timeDialog.show(fragmentManager, "")
    }

    private fun initSpinGolongan() {
        typeAdapter = ArrayAdapter(this, R.layout.spinner_item, typeStr)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinGolongan.adapter = typeAdapter
        spinGolongan.setSelection(0, false)
        spinGolongan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i != 0) {
                    val id = typeList[i - 1].id.default
                    selectedType = id
                    presenter.getDrug(id)
                } else {
                    selectedType = 0
                    drugStr.clear()
                    drugStr.add(drugDef)
                    drugAdapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun initSpinObat() {
        drugAdapter = ArrayAdapter(this, R.layout.spinner_item, drugStr)
        drugAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinObat.adapter = drugAdapter
        spinObat.setSelection(0, false)
        spinObat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedDrug = if (i != 0) drugList[i - 1].id.default else 0
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun initData(alarm: Alarm) {
        id = alarm.id.default
        typeId = alarm.drugTypeId?.toInt().default
        drug = alarm.name.default
        consumed = alarm.consumed.default
        consumedTime = alarm.consumedTime.default
        dosis = alarm.dosage.default

        etOtherName.setText(alarm.otherName)
        etJumlah.setText(alarm.qty)

        when (alarm.consumed) {
            CONSUMED_WITH -> radio_when.isChecked = true
            getString(R.string.sesudah_makan) -> radio_after.isChecked = true
            else -> radio_before.isChecked = true
        }

        when (alarm.consumedTime) {
            getString(R.string._15_menit) -> radio_15m.isChecked = true
            getString(R.string._30_menit) -> radio_30m.isChecked = true
            getString(R.string._1_jam) -> radio_1h.isChecked = true
            else -> {
                groupMinute.gone()
                tvWhenEat.visible()
            }
        }

        when (alarm.dosage) {
            "1" -> radio_1times.isChecked = true
            "2" -> radio_2times.isChecked = true
            "3" -> radio_3times.isChecked = true
            "4" -> radio_4times.isChecked = true
        }

        alarm.times?.let { list ->
            val times = mutableListOf<String>()
            list.mapTo(times) { it.time.default }
            clockAdapter.updateList(times)
        }
    }

    private fun submitForm() {
        if (selectedType == 0) {
            showPopup("Mohon pilih golongan obat")
            return
        }

        if (selectedDrug == 0) {
            showPopup("Mohon pilih nama obat")
            return
        }

        if (etJumlah.isEmpty()) return

        val str = "Gol: $selectedType, Nama: $selectedDrug, Cosumed: $consumed, " +
                "Consumed Time: $consumedTime, Dosis: $dosis"
        Timber.d("Form: $str")

        val clock = clockAdapter.getAllItem()
        val times = mutableListOf<Times>()
        clock.mapTo(times) { Times(time = it) }

        val form = Alarm(drugTypeId = selectedType.toString(), drugId = selectedDrug.toString(),
                otherName = etOtherName.text.toString(), qty = etJumlah.text.toString(),
                consumed = consumed, consumedTime = consumedTime, dosage = dosis, times = times)

        if (id == 0) presenter.addAlarm(form)
        else presenter.editAlarm(id, form)
    }
}