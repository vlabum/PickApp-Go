package ru.vlabum.pickappngo.ui.delegates

import ru.vlabum.pickappngo.ui.base.Binding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// делегат, который будет отрисовывать наши проперти
class RenderProp<T: Any>(
    var value: T, // значение поля
    private val needInit: Boolean = true, // надо ли вызывать обработчик при инициализации или не надо
    private val onChange: ((T) -> Unit)? = null // обработчик, устанавливает значение для вьюхи при изменении нашего проперти
) : ReadWriteProperty<Binding, T> {

    private val listeners: MutableList<() -> Unit> = mutableListOf()

    fun bind() {
        if (needInit) onChange?.invoke(value)
    }

    operator fun provideDelegate(
        thisRef: Binding,
        prop: KProperty<*>
    ): ReadWriteProperty<Binding, T> {
        val delegate = RenderProp(value, needInit, onChange)
        registerDelegate(thisRef, prop.name, delegate)
        return delegate
    }

    override fun getValue(thisRef: Binding, property: KProperty<*>): T = value

    override fun setValue(thisRef: Binding, property: KProperty<*>, value: T) {
        if (value == this.value) return
        this.value = value
        onChange?.invoke(this.value)
        //при установке значения, нужно вызвать все лисенеры
        if (listeners.isNotEmpty()) listeners.forEach { it.invoke() }
    }

    //register additional listener
    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    private fun registerDelegate(thisRef: Binding, name: String, delegate: RenderProp<T>) {
        thisRef.delegates[name] = delegate
    }

}

class ObserveProp<T : Any>(private var value: T, private val onChange: ((T) -> Unit)? = null) {
    //provide delegate (when by call)
    operator fun provideDelegate(
        thisRef: Binding,
        prop: KProperty<*>
    ): ReadWriteProperty<Binding, T> {
        val delegate = RenderProp(value, true, onChange)
        registerDelegate(thisRef, prop.name, delegate)
        return delegate
    }

    // register new delegate for property in Binding
    private fun registerDelegate(thisRef: Binding, name: String, delegate: RenderProp<T>) {
        thisRef.delegates[name] = delegate
    }
}