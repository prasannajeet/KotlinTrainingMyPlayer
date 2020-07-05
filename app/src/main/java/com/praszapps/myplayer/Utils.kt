package com.praszapps.myplayer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.getLayoutInflater() : LayoutInflater = LayoutInflater.from(this.context)

fun RecyclerView.ViewHolder.showToast(message: String) {
    Toast.makeText(this.itemView.context, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadUrl(@NonNull url: String) {
    Glide.with(this).load(url).into(this)
}

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>)  =

        Intent(this, T::class.java).apply {putExtras(bundleOf(*pairs)) }.also(::startActivity)
    // wont work without reified since type-erasure

fun test () {
    val sum = {x: Int, y: Int -> x+y}
    val mul : (Int, Int) -> Int = {x, y -> x*y}

    doOp(3, 4, sum)
    doOp(3, 4, mul)
    doOp(3, 4) { x, y -> x-y } // If lambda is last argument then it can be removed out of parenthesis, like with function

    doOp2(3) {x -> x*x}
    // Can be rewritten as
    doOp2(4) {it*it}
}

fun doOp(x: Int, y: Int, operation: (Int, Int) -> Int) = operation(x, y)

fun doOp2(x: Int, op: (Int) -> Int) = op(x)

typealias MediaItemClickListener = (MediaItem) -> Unit

sealed class Filter {
    class ByType(val type: MediaItem.Type) : Filter()
    object None : Filter()
}