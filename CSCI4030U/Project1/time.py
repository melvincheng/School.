import sys
f = open(sys.argv[1],'r')
threshold = ""
flag = True
for line in f:
	if(line[0:22] == "Threshold percentage: "):
		if (threshold != line[22:-1]):
			threshold = line[22:-1]
			flag = True
		else:
			flag = False
	if(line[0:5] == "real	"):
		for i in range(0,len(line)):
			if(line[i] == "m"):
				m = i
				break
		# print(line[m+1:])

		minutes = float(line[5:m])*60
		seconds = float(line[m+1:-2])
		# minutes = line[5:m]
		# seconds = line[m+1:-2]
		# time = minutes + ":" + seconds
		time = minutes + seconds
		
		if flag:
			sys.stdout.write("\n"+threshold+","+"%s"%time+",")
		else:
			sys.stdout.write("%s"%time+",")