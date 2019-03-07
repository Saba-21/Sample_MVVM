package com.saba.sampleMVVM.base.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> T.makeObservable():
        Observable<T> = Observable.just<T>(this)

fun <T> (() -> T).fromCallable():
        Observable<Boolean> = Observable.fromCallable {
    this.invoke()
    true
}

fun <T> Observable<T>.async():
        Observable<T> = this
    .subscribeOn(Schedulers.newThread())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.toLiveData():
        LiveData<T> = LiveDataReactiveStreams
    .fromPublisher(
        this.toFlowable(BackpressureStrategy.LATEST)
    )