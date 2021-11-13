package com.imdatcandan.wundercars.common

interface ModelMapper<D, E> {
    fun mapToModel(data: D): E
}