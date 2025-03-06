package com.unknowncat.orients.tools

import org.springframework.data.jpa.repository.JpaRepository
import kotlin.random.Random

class Repositories {
    companion object{

        class Snowflake(private val machineId: Long) {
            private var sequence = 0L
            private var lastTimestamp = -1L

            @Synchronized
            fun nextId(): Long {
                var timestamp = System.currentTimeMillis()
                if (timestamp < lastTimestamp) {
                    throw RuntimeException("Clock moved backwards")
                }
                if (timestamp == lastTimestamp) {
                    sequence = (sequence + 1) and 0xFFF
                    if (sequence == 0L) {
                        timestamp = tilNextMillis(lastTimestamp)
                    }
                } else {
                    sequence = 0L
                }
                lastTimestamp = timestamp
                return (timestamp shl 22) or (machineId shl 12) or sequence
            }

            private fun tilNextMillis(last: Long): Long {
                var timestamp = System.currentTimeMillis()
                while (timestamp <= last) timestamp = System.currentTimeMillis()
                return timestamp
            }
        }
        val snowflake: Snowflake = Snowflake(-89743107324435)

        fun <T> generateUniqueId(jpaRepository: JpaRepository<T, Long>): Long{
            return snowflake.nextId()
        }
    }
}