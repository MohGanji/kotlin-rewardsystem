package rewardSystem

class SubPartner(id: Long, parent: Partner) : Partner(id) {
    val parent: Partner = parent

    fun getRewardDifference(level: PartnerLevel, year: Int, quarter: Int): Long {
        var overall: Long = 0
        val validContracts =
            contracts.filter { it.validFor(year, quarter) }.size
        overall += validContracts * (level.rewardPerContract() - this.getLevelFor(year, quarter).rewardPerContract())
        this.subPartners.forEach {
            overall += it.getRewardDifference(level, year, quarter)
        }
        return overall
    }
}