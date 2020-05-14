package adjie.santooso.edittextcurrency

import android.annotation.SuppressLint
import android.content.Context
import android.text.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat
import java.util.*

class EditTextCurrency @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    var currencyLocale: Locale = Locale("id", "ID")

    private fun getCurrency(): Currency = Currency.getInstance(currencyLocale)
    private fun getCurrencySymbol(): String = getCurrency().getSymbol(currencyLocale)

    override fun onFinishInflate() {
        super.onFinishInflate()
        val inputFilter = object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                for (i in start until end) {
                    val char = source!![i]
                    if (!Character.isDigit(char)) {
                        return if (char == ',' || char == '.' || getCurrencySymbol().contains(char)) {
                            null
                        } else ""
                    }
                }

                return null
            }

        }

        filters = arrayOf(inputFilter)
        inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

        addTextChangedListener(totalTextWatcher)
    }

    private val totalTextWatcher = object : TextWatcher {
        private fun getCurrency(): Currency = Currency.getInstance(currencyLocale)
        private fun getCurrencySymbol(): String = getCurrency().getSymbol(currencyLocale)

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        @SuppressLint("SetTextI18n")
        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            s?.trim()?.let {
                removeTextChangedListener(this)

                val numberFormatter = DecimalFormat("#,###.##")
                numberFormatter.minimumFractionDigits = 0
                numberFormatter.maximumFractionDigits = 2
                numberFormatter.isDecimalSeparatorAlwaysShown = false

                val displayCurrencySymbol = getCurrencySymbol() + " "
                var result = it.toString().replace(displayCurrencySymbol, "")
                result = result.replace(displayCurrencySymbol.trim(), "")

                if (result.isNotEmpty() && result.toString().last().isDigit()) {
                    val parsedValue = try {
                        numberFormatter.parse(result)
                    } catch (e: Exception) {
                        0.0
                    }

                    result = numberFormatter.format(parsedValue)
                }

                val displayedText = displayCurrencySymbol + result
                setText(displayedText)
                setSelection(displayedText.length)

                addTextChangedListener(this)
            }
        }
    }
}