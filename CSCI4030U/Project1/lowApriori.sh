for ((i = 10000;i<=80000;i = i + 10000));
do
	printf "%5s %10s\n\r" $i baskets
	for k in {1..5};
	do
		printf "%5s %10s\n\r" $k run
		(time ./$1 2 $2 $i) &>> $3; 
	done;
done;
 python timePcy.py $3 &>> $4