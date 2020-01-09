package rewardSystem

import dateUtils.Date

class Contract(id: Long, date: Date, type: ContractType) {
    val id: Long = id
    val startDate: Date = date
    private var endDate: Date? = null
    val type: ContractType = type

    fun end(date: Date) {
        endDate = date
    }

    fun expired(year: Int, quarter: Int): Boolean {
        val endDateConstraint: Boolean =
            if (endDate != null) endDate!!.year < year || endDate!!.year == year && endDate!!.quarter > quarter else false
        val eightYearConstraint: Boolean =
            year > this.startDate.year + 8 || (year == this.startDate.year + 8 && quarter > this.startDate.quarter)
        return endDateConstraint || eightYearConstraint
    }

    fun validFor(year: Int, quarter: Int): Boolean {
        return !expired(year, quarter) && year >= this.startDate.year && quarter == this.startDate.quarter
    }
}

//REGISTER 1
//REGISTER 2 1
//REGISTER 3 2
//LOAD /Users/m0hammad/Misc/Tapsell/sales.csv
//LEVEL 1 2002 1
//LEVEL 1 2002 2
//LEVEL 1 2002 3
//LEVEL 1 2012 2
