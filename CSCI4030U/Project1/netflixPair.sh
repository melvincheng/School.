for ((i = 75000;i<450000;i = i + 75000));
do
	printf "%5s %10s\n" $i baskets
	for j in {5000,25000,150000,250000}; 
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