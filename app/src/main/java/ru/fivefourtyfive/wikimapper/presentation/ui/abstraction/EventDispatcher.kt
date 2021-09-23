package ru.fivefourtyfive.wikimapper.presentation.ui.abstraction

interface EventDispatcher<EVENT : Event> {

    fun dispatchEvent(event: EVENT)
}