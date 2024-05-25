package com.faza.quippertest.common

interface Equatable {
    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}