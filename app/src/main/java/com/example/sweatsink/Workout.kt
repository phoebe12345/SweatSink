class Workout{
    var name=""
    var sets=0
    var reps=0
    var restMins=0
    var isValid=true //is this a valid Workout?
    //var restSecs=0

    constructor(inputString:String){
        var index=0

        for(i in 0..inputString.length-3){
            if(inputString.substring(i,i+3)==" - ") {//we reach the end of the name part of the string
                index=i+3//set index to the character just after " - "
                break
            }
            else//otherwise keep adding the string to name
                name+=inputString[i]
        }
        if(name.isBlank()){
            isValid=false
            return
        }

        index=extract(index,inputString,"sets")
        if(!isValid)
            return
        index=extract(index,inputString,"reps")
        if(!isValid)
            return
        extract(index,inputString,"restMins")
    }

    //returns new index
    private fun extract(index:Int,inputString:String,target:String):Int{
        var string=""
        var reachedDigits=false
        for(i in index until inputString.length){
            if(inputString[i].isDigit()) {
                string += inputString[i]
                reachedDigits=true
            }else if(reachedDigits){
                if(target=="sets")
                    sets=string.toInt()
                else if(target=="reps")
                    reps=string.toInt()
                else if(target=="restMins")
                    restMins=string.toInt()

                return i+1
            }
        }
        if(string.isEmpty())
            isValid=false
        return 0
    }

    override fun toString():String{
        return "$name - $sets sets, $reps reps, $restMins minutes rest"
    }
}
