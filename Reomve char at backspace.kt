package com.pg.scoalview

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mapData = HashMap<String, Boolean>()
    var boldString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boldString = "Hello <b>Purva</b> how <b>Fine</b> "

        textView.setText(Html.fromHtml(boldString))
        mapData["Purva"] = true
        mapData["Fine"] = true


        /*var split = "Hello <b>Purva</b> How <b>Are<b/> you".split(" ")
        split
        var array = ArrayList<String>()
        for (data in split) {
            if (!data.contains("Pur")) {
                array.add(data)
            }
        }
        array*/


        textView.addTextChangedListener(object : TextWatcher {
            private var prevLength = 0
            private var isBackPressed = false
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence!!.length <= 1) {
                    mapData.clear()
                }
                isBackPressed = prevLength > charSequence.toString().length

                if (isBackPressed) {
                    Toast.makeText(
                        getApplicationContext(),
                        "Back press keyboard ${textView.text.toString()}",
                        Toast.LENGTH_LONG
                    ).show();

                    boldString = textView.text.toString()
                    for (data in mapData.keys) {
                        boldString = boldString.replace(data, "<b>$data</b>")
                    }

                    val spaceArray = textView.text.toString().split(" ")
                    for (dataFromMentionedArray in mapData.keys) {
                        var isFound = false
                        for (data in spaceArray) {
                            if (dataFromMentionedArray == data) {
                                isFound = true
                                break
                            }
                        }
                        if (!isFound) {
                            mapData[dataFromMentionedArray] = false
                        }
                    }

                    val map = mapData.filterValues { it == false }
                    if (map.isNotEmpty()) {
                        for (removeData in map.keys) {
                            mapData.remove(removeData)
                        }
                        val split = boldString.split(" ")
                        val array = ArrayList<String>()
                        for (notMatched in map.keys) {
                            for (data in split) {
                                if (!data.contains(notMatched)) {
                                    array.add(data)
                                }
                            }
                        }

                        boldString = array.filter { it.isNotEmpty() }.joinToString(" ").trim()
                        Log.d("Merge ", "Merge ${array.joinToString(" ")} $boldString")

                        val a = array.joinToString(" ")
                        textView.setText(Html.fromHtml(a))
                        textView.setSelection(textView.length())
                    }
                }
                prevLength = charSequence.toString().length
            }

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                prevLength = charSequence.toString().length


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

    }
}
