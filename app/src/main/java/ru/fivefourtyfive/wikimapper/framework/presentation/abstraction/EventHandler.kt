package ru.fivefourtyfive.wikimapper.framework.presentation.abstraction

interface EventHandler<EVENT : Event> {
    fun handleEvent(event: EVENT)
}