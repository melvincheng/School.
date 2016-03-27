def makePerson(iname:String,iage:Int) = {
	var agestore = iage
	val age = () => {agestore}
	val name = () => {iname}
	val grow = () => {agestore = agestore + 1; (agestore)}
	(age,name,grow)
}

val (age,name,grow) = makePerson("Clark Kent", 28)
println(age())
println(name())
println(grow())
println(grow())
println(grow())