for ((i = 10000;i<=80000;i = i + 10000));
do
	printf "%5s %10s\n" $i baskets
	for j in {2,100,500,1000,5000,10000}; 
	do
		printf "%5s %10s\n" $j threshold
		for k in {1..5};
		do
			printf "%5s %10s\n" $k run
			(time ./$1 $j $2 $i) &>> $3; 
		done;
	done; 
done;
python timePcy.py $3 &>> $4