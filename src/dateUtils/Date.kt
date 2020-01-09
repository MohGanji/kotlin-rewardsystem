package dateUtils

class Date(date: String) {
    val year: Int = date.split('-')[0].toInt()
    val quarter: Int = date.split('-')[1].toInt()/3 + 1
}