package ru.fivefourtyfive.wikimapper.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelProviderFactory
@Inject
constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        val creator = creators[viewModelClass] ?: creators.asIterable()
            .firstOrNull { viewModelClass.isAssignableFrom(it.key) }?.value
        ?: throw IllegalArgumentException("Unknown ViewModel class: [$viewModelClass]")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}