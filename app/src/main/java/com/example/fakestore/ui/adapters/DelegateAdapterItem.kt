package com.example.fakestore.ui.adapters

interface DelegateAdapterItem {

    fun id(): Any

    fun content(): Any

    fun payload(other: Any): Payloadable = Payloadable.None

    interface Payloadable{
        object None: Payloadable
    }
}