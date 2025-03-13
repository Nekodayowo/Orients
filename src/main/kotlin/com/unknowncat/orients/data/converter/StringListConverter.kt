package com.unknowncat.orients.data.converter

import com.unknowncat.orients.data.converter.StringListConverter.Companion.SPLIT_CHAR
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*

@Converter
class StringListConverter: AttributeConverter<List<String>, String> {
    companion object{
        const val SPLIT_CHAR = ";"
    }

    override fun convertToDatabaseColumn(strings: List<String>?): String {
        if (strings == null) return ""
        return join(strings)
    }

    override fun convertToEntityAttribute(p0: String?): List<String> {
//        TODO("Not yet implemented")
        if(p0 == null) return emptyList()
        return arrayListOf<String>().apply { addAll(p0.split(SPLIT_CHAR)) }
    }
    private fun join(strings: List<String>): String {
        val stringBuilder = StringBuilder()
        for (string in strings) {
            stringBuilder.append(string)
            stringBuilder.append(SPLIT_CHAR)
        }
        return stringBuilder.toString().substring(0, stringBuilder.length - 1)
    }

}