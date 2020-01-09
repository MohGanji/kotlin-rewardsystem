package rewardSystem

open class Partner(id: Long) {

    val id = id
    val contracts: MutableList<Contract> = mutableListOf()
    protected val subPartners: MutableList<SubPartner> = mutableListOf()

    fun addSubPartner(subPartner: SubPartner) {
        subPartners.add(subPartner)
    }

    fun getSumContracts(year: Int, quarter: Int): Int {
        var sumContracts = contracts.filter { it.validFor(year, quarter) }.size
        subPartners.forEach {
            sumContracts += it.getSumContracts(year, quarter)
        }
        return sumContracts
    }

    fun getLevelFor(year: Int, quarter: Int): PartnerLevel {

        return when (getSumContracts(year, quarter)) {
            0 -> PartnerLevel.Ant
            in PartnerLevel.Ant.minContract() until PartnerLevel.Bee.minContract() -> PartnerLevel.Ant
            in PartnerLevel.Bee.minContract() until PartnerLevel.Cat.minContract() -> PartnerLevel.Bee
            in PartnerLevel.Cat.minContract() until PartnerLevel.Dog.minContract() -> PartnerLevel.Cat
            in PartnerLevel.Dog.minContract() until PartnerLevel.Elephant.minContract() -> PartnerLevel.Dog
            else -> PartnerLevel.Elephant
        }
    }

    fun getRewardFor(year: Int, quarter: Int): Long {
        val level = getLevelFor(year, quarter)
        var overall: Long = 0
        contracts.forEach {
            if(it.startDate.year == year && it.startDate.quarter == quarter) {
                overall += level.rewardPerContract() + it.type.bonusReward()
            }
        }
        subPartners.forEach {
            overall += it.getRewardDifference(level, year, quarter)
        }
        return overall
    }

    fun getAllRewards(): List<IRewardOutput> {
        var rewards: MutableList<IRewardOutput> = mutableListOf()
        for (y in 2020 downTo 2000) {
            for (q in 1..4) {
                val reward : IRewardOutput = object:IRewardOutput {
                    override val year: Int = y
                    override val quarter: Int = q
                    override val reward: Long = getRewardFor(y, q)
                }
                rewards.add(reward)
            }
        }
        return rewards
    }

}
