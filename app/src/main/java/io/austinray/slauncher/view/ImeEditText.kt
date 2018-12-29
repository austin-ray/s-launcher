package io.austinray.slauncher.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText

/**
 * An extension EditText, which allows function calls on IME events.
 */
class ImeEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    // Higher-order function for various IME events.
    var imeActionDone = {}
    var onBackPress = {}

    init {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) imeActionDone()
            true
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) onBackPress

        return super.onKeyPreIme(keyCode, event)
    }
}
