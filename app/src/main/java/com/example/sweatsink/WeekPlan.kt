class WeekPlan {
    var days=arrayOfNulls<DayPlan>(7)
    enum class Day{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY}

    constructor(inputString:String){
        var indexes= arrayOfNulls<Int>(7)
        for(i in indexes.indices){
            indexes[i]=inputString.indexOf(Day.values()[i].toString())
        }

        for(i in 0..days.size-2){
            days[i]= indexes[i]?.let { indexes[i+1]?.let { it1 -> inputString.substring(it, it1) } }?.let { DayPlan(it) }
        }
        days[6]= indexes[6]?.let { inputString.substring(it) }?.let { DayPlan(it) }
    }

    override fun toString():String{
        var stringToReturn=""
        for(i in days.indices) {
            stringToReturn+=(Day.values()[i].toString()+':'+'\n'+days[i]?.toString()+'\n')
        }
        return stringToReturn
    }
}
