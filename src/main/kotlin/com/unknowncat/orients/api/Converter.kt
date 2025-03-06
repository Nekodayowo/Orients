package com.unknowncat.orients.api

interface Converter<Source, Target> {
    fun convert(obj: Source): Target

    fun inverse(obj: Target): Source?
}