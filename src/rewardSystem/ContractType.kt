package rewardSystem

enum class ContractType {
    Tortoise {
        override fun bonusReward(): Long { return 0 }
        override fun price(): Long { return 100 }
    },
    Rabbit {
        override fun bonusReward(): Long { return 50 }
        override fun price(): Long { return 300 }
    };

    abstract fun bonusReward(): Long
    abstract fun price(): Long
}