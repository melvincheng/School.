import sys
f = open(sys.argv[1],'r')
threshold = ""
baskets = ""
flag = True
for line in f:
	
	if line[0:11] == "Threshold: ":
		if (threshold != line[11:-1]):
			threshold = line[11:-1]
			flag = True
		else:
			flag = False
	if line[0:9] == "Baskets: ":
		if line[9:-1] != baskets:
			baskets = line[9:-1]
			bFlag = True
		else:
			bFlag = False

	if line[0:5] == "real	":
		for i in range(0,len(line)):
			if line[i] == "m" :
				m = i
				break
		# print(line[m+1:])
		minutes = float(line[5:m])*60
		seconds = float(line[m+1:-2])
		# minutes = line[5:m]
		# seconds = line[m+1:-2]
		# time = minutes + ":" + seconds
		time = minutes + seconds
		sys.stdout.write(baskets+","+threshold+","+"%s"%time+"\n")