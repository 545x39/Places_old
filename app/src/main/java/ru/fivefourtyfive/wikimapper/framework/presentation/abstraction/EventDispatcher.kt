package ru.fivefourtyfive.wikimapper.framework.presentation.abstraction

interface EventDispatcher<EVENT : Event> {

    fun dispatchEvent(event: EVENT)
}