import io.CommandLineInterpreter
import rewardSystem.RewardService

fun main() {
    val rs = RewardService()
    val cli = CommandLineInterpreter(rs)
    cli.listen()
}