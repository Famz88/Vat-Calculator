package com.fahmy.vatcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



private const val TAG = "MainActivity"
private const val INITIAL_VAT_PERCENTAGE = 5
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarVat: SeekBar
    private lateinit var tvVatPercentLabel: TextView
    private lateinit var tvVatAmount: TextView
    private lateinit var tvTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarVat = findViewById(R.id.seekBarVat)
        tvVatPercentLabel = findViewById(R.id.tvVatPercentLabel)
        tvVatAmount = findViewById(R.id.tvVatAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        seekBarVat.progress = INITIAL_VAT_PERCENTAGE
        tvVatPercentLabel.text = "$INITIAL_VAT_PERCENTAGE%"
        seekBarVat.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvVatPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, berore: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })
    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()) {
            tvVatAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        //  1. Get the value of the base and tip percent
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val vatPercent = seekBarVat.progress

        //  2. Compute the tip and total
        val VatAmount = baseAmount * vatPercent / 100
        val totalAmount = baseAmount - VatAmount

        //  3. Update the UI
        tvVatAmount.text = "%.2f".format(VatAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}