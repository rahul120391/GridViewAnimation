package com.example.sampleproject.extensions

import android.view.View

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}