for ((i=50;i>0;i = i - 5)); 
do
	printf "%5s %10s\n" $i threshold
	for k in {1..5};
	do
		printf "%5s %10s\n" $k run
		(time go run $1 $i $2) &>> $3; 
	done; 
done;
python time.py $3 &>> $4