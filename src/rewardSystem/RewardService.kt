package rewardSystem

import dateUtils.Date

class RewardService {
    private var partners : MutableList<Partner> = mutableListOf()

    fun registerPartner(partnerId: Long) {
        if(partners.any { it.id == partnerId }) throw Error("Partner with id='$partnerId' exists in system")

        partners.add(Partner(partnerId))
    }

    fun registerSubPartner(partnerId: Long, parentPartnerId: Long) {
        if(partners.any { it.id == partnerId }) throw Error("Partner with id='$partnerId' exists in system")
        if (partners.none { it.id == parentPartnerId }) throw Error("Parent Partner with id='$parentPartnerId' does not exist")

        val parent: Partner = partners.find {it.id == parentPartnerId}!!
        val subPartner = SubPartner(partnerId, parent)

        parent.addSubPartner(subPartner)
        partners.add(subPartner)
    }

    fun getPartnerLevel(partnerId: Long, year: Int, quarter: Int): String {
        return partners.find { it.id == partnerId }!!.getLevelFor(year, quarter).name
    }

    fun getPartnerReward(partnerId: Long, year: Int, quarter: Int): Long {
        return partners.find { it.id == partnerId }!!.getRewardFor(year, quarter)
    }

    fun getPartnerAllRewards(partnerId: Long): List<IRewardOutput> {
        return partners.find { it.id == partnerId }!!.getAllRewards()
    }

    fun addSalesReportItem(partnerId: Long, contractId: Long, contractType: ContractType, date: Date, action: ActionType) {
        when (action) {
            ActionType.BEGIN -> {
                val contract = Contract(contractId, date, contractType)
                partners.find { it.id == partnerId }!!.contracts.add(contract)
            }
            ActionType.END -> {
                partners.find { it.id == partnerId }!!.contracts.find { it.id == contractId }!!.end(date)
            }
        }
    }


}