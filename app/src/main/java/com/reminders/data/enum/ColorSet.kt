package com.reminders.data.enum

import com.reminders.R
import com.reminders.data.model.ColorBlock

object ColorSet{
    val data: List<ColorBlock> = listOf(
        ColorBlock(R.color.black, "red", R.color.white),
        ColorBlock(R.color.red, "red", R.color.white),
        ColorBlock(R.color.blue, "red", R.color.white),
        ColorBlock(R.color.purple_500, "red", R.color.white),
        ColorBlock(R.color.white, "red", R.color.black),
    )
}