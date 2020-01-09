package rewardSystem

enum class PartnerLevel {
    Ant {
        override fun rewardPerContract(): Int { return 5 }
        override fun minContract(): Int { return 1 }
    },
    Bee {
        override fun rewardPerContract(): Int { return 7 }
        override fun minContract(): Int { return 10 }
    },
    Cat {
        override fun rewardPerContract(): Int { return 9 }
        override fun minContract(): Int { return 50 }
    },
    Dog {
        override fun rewardPerContract(): Int { return 12 }
        override fun minContract(): Int { return 200 }
    },
    Elephant {
        override fun rewardPerContract(): Int { return 19 }
        override fun minContract(): Int { return 1000 }
    };


    abstract fun rewardPerContract(): Int
    abstract fun minContract(): Int


}