package com.reminders.data.enum

import com.reminders.R
import com.reminders.data.model.ColorBlock

object ColorSet{
    val data: List<ColorBlock> = listOf(
        ColorBlock(R.color.black, "black", R.color.white),
        ColorBlock(R.color.orange, "orange", R.color.white),
        ColorBlock(R.color.dark_blue, "dark_blue", R.color.white),
        ColorBlock(R.color.yellow, "yellow", R.color.black),
        ColorBlock(R.color.cyan, "cyan", R.color.black),
        ColorBlock(R.color.white, "white", R.color.black),
    )
}