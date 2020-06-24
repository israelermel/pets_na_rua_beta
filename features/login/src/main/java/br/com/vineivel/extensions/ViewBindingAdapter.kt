package br.com.vineivel.extensions

import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import br.com.vineivel.domain.LoadingState
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("loading_state")
fun ContentLoadingProgressBar.bindLoadingState(loadingState: LoadingState?) {
    loadingState?.let {
        renderLoading(this, it)
    }
}

private fun renderLoading(
    view: ContentLoadingProgressBar,
    loadingState: LoadingState
) {
    when (loadingState) {
        is LoadingState.Loading -> view.toLoading()
        is LoadingState.UnLoad -> view.toUnload()
    }
}

@BindingAdapter("error_message")
fun TextInputLayout.bindErrorMessage(errorMessage: LiveData<String>) {
    errorMessage.value?.let { error ->
        this.error = error
    }
}

@BindingAdapter("error_message_state")
fun TextInputLayout.bindErrorMessageState(errorMessageState: LiveData<ErrorMessageState>) {
    errorMessageState.value?.let { error ->
        when (error) {
            ErrorMessageState.SHOW -> {
            }

            ErrorMessageState.HIDE -> {
                this.error = null
            }
        }
    }
}