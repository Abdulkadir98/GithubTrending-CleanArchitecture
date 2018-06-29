package com.example.presentation.mapper

interface Mapper <out V, in D> {

    public fun mapToView(type: D) : V
}