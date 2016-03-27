var ilist:List[Int] = List(10,9,8,7,6,5,4,3,2,1,0)
var slist:List[String] = List("g","i","r","w","e","a","p")

def compare[K <% Ordered[K]](num1:K,num2:K) = {
	if(num1 > num2){
		(1);
	}else if(num1 < num2){
		(-1);
	}else{
		(0);
	}
}
def quicksort[K <% Ordered[K]](comparator:(K,K)=>Int)(input:List[K]):List[K]={
	if(input.size <= 1) input
	else{
		val p = input(input.size/2)
		quicksort[K](comparator)(input.filter(i=>comparator(p,i) == 1)):::input.filter(i=>comparator(p,i) == 0):::quicksort[K](comparator)(input.filter(i=>comparator(p,i) == -1))
	}
}

println(quicksort[Int](compare)(ilist))

var numquick = quicksort[String](compare)_
println(numquick(slist))