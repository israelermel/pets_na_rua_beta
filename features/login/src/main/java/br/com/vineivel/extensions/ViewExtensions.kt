package br.com.vineivel.extensions

import androidx.core.widget.ContentLoadingProgressBar

fun ContentLoadingProgressBar.toLoading() = this.show()

fun ContentLoadingProgressBar.toUnload() = this.hide()