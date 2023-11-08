class DayPlan{
    var workouts=mutableListOf<Workout>()
    var isRest=false //is it a rest day (no workouts)

    constructor(inputString: String){
        var currentString=""
        for(i in inputString.indices){
            if(inputString[i]=='\n') {
                val w=Workout(currentString)
                if(w.isValid) {
                    workouts.add(Workout(currentString))
                }
                currentString=""
            }else{
                currentString+=inputString[i]
            }
        }
        if(currentString.isNotEmpty()) {
            val w=Workout(currentString)
            if(w.isValid) {
                workouts.add(Workout(currentString))
            }
        }

        if(workouts.isEmpty())
            isRest=true
    }

    override fun toString():String{
        if(isRest)
            return "rest day\n"
        else {
            var stringToReturn=""
            for (i in 0 until workouts.size) {
                stringToReturn+=(workouts[i].toString()+'\n')
            }
            return stringToReturn
        }
    }
}
