package io

import dateUtils.Date
import rewardSystem.ActionType
import rewardSystem.ContractType
import rewardSystem.RewardService
import java.io.File
import java.lang.Exception

class CommandLineInterpreter(rewardService: RewardService) {

    private val rs: RewardService = rewardService

    fun listen() {
        loop@ while (true) {
            try {
                val rawCmd = readLine()!!.run { trim(' ').split(" ") }
                val cmd = rawCmd.first()
                val args = rawCmd.drop(1)
                when (cmd) {
                    in "exit", "EXIT", "end", "END", "close", "CLOSE" -> break@loop
                    "REGISTER" -> {
                        println(
                            if (args.size in 1..2) {
                                handleRegister(args[0], args.getOrNull(1))
                            } else "Usage: REGISTER partnerId [parentPartnerId]"
                        )
                    }
                    "LOAD" -> {
                        println(
                            if (args.size == 1) {
                                handleLoad(args[0])
                            } else "Usage: LOAD filename"
                        )
                    }
                    "LEVEL" -> {
                        println(
                            if (args.size == 3) {
                                handleLevel(args[0], args[1], args[2])
                            } else "Usage: LEVEL partnerId year quarter"
                        )
                    }
                    "REWARDS" -> {
                        println(
                            if (args.size == 3) {
                                handleRewards(args[0], args[1], args[2])
                            } else "Usage: REWARDS partnerId year quarter"
                        )
                    }
                    "ALL_REWARDS" -> {
                        println(
                            if (args.size == 1) {
                                handleAllRewards(args[0])
                            } else "Usage: ALL_REWARDS partnerId"
                        )
                    }
                    else -> println(
                        """
                    List of available commands:
                    -- REGISTER partnerId [parentPartnerId]
                    -- LOAD filename
                    -- LEVEL partnerId year quarter
                    -- REWARDS partnerId year quarter
                    -- ALL_REWARDS partnerId
                """.trimIndent()
                    )
                }
            } catch (e: Exception) {
                println("Exception: ${e.printStackTrace()}")
            } catch (e: Error) {
                println("Error: ${e.message}")
            }
        }
    }

    private fun handleRegister(partnerId: String, parentPartnerId: String?): String {
        if (parentPartnerId.isNullOrEmpty()) rs.registerPartner(partnerId.toLong()) else rs.registerSubPartner(
            partnerId.toLong(),
            parentPartnerId.toLong()
        )
        return "Registered Partner with id='$partnerId' successfully"
    }

    private fun handleLoad(filename: String): String {
        File(filename).forEachLine() {
            val args = it.trim().split(',').map { s -> s.trim() }
            require(args.size == 5)
            rs.addSalesReportItem(
                args[0].toLong(),
                args[1].toLong(),
                ContractType.valueOf(args[2]),
                Date(args[3]),
                ActionType.valueOf(args[4])
            )
        }
        return "loaded records successfully"
    }

    private fun handleLevel(partner: String, year: String, quarter: String): String {
        return rs.getPartnerLevel(partner.toLong(), year.toInt(), quarter.toInt())
    }

    private fun handleRewards(partnerId: String, year: String, quarter: String): String {
        return rs.getPartnerReward(partnerId.toLong(), year.toInt(), quarter.toInt()).toString()
    }

    private fun handleAllRewards(partnerId: String): String {
        return rs.getPartnerAllRewards(partnerId.toLong())
            .joinToString("\n", transform = { "${it.year} ${it.quarter} ${it.reward}" })
    }

}
