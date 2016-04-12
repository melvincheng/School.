for ((i=1;i<$1;i=i+1))
do
for in in *.arff
do
	base=$(basename $in)
	dirName=${base%.*}_$i
	echo $dirName
	mkdir $dirName
	cp run.sh $dirName
	java -cp ~/weka-3-7-13/weka.jar weka.filters.unsupervised.attribute.Remove -R $i -i $in -o ./$dirName/$dirName$i.arff
	cd $dirName
	newArg=$(($1-1))
	chmod u+x run.sh 
	if [ $1 -lt 8 ]
	then
	./run.sh $newArg &
else
	./run.sh $newArg
	fi
	cd ..
done;
done;