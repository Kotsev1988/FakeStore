package com.example.fakestore.ui.network


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


interface INetworkStates {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}