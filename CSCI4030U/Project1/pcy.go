package main

import(
"fmt"
"bufio"
"os"
"strings"
"strconv"
"io"
"log"
)

func main(){
	usrin := os.Args
	//argument 1 is threshold
	//argument 2 is file name
	//argument 3 is number of baskets, 
	//if user enters value under 1, entire data is used

	if(len(usrin) > 3){
		threshold, err := strconv.Atoi(usrin[1])
		basket, err2 := strconv.Atoi(usrin[3])
		if err == nil && err2 == nil{
			pcy(threshold, usrin[2], basket)
			os.Exit(0)
		}else if err != nil{
			log.Fatal(err)
		}else if err2 != nil{
			log.Fatal(err2)
		}
	}else{
		log.Fatal("Not enough arguments")
	}
}


func pcy(threshold int, file string, basket int){
	var singleCount int
	var hashCount int
	var tot int
	var hash int
	var singlemap map[string]int
	singlemap = make(map[string]int)
	var hashmap map[int]int
	hashmap = make(map[int]int)
	var convErrOne, convErrTwo error
	var intOne, intTwo int
	var newString []string

	//pass 1
	in,inErr := os.Open(file)
	if(inErr == nil){
		reader := bufio.NewReader(in)
		str,readErr:=reader.ReadBytes('\n')
		for readErr==nil{
			newString = strings.Split(string(str)," ")
			for i:=0;i<len(newString)-1;i++{
				singleCount = singlemap[newString[i]]
				singleCount++
				singlemap[newString[i]] = singleCount
				for j:=i;j<len(newString)-1;j++{	
					intOne, convErrOne = strconv.Atoi(newString[i])
					if convErrOne != nil{
						log.Panic(convErrOne)
					}
					intTwo, convErrTwo = strconv.Atoi(newString[j])
					if convErrOne != nil{
						log.Panic(convErrTwo)
					}							
					hash = (intOne ^ intTwo)%2500
					hashCount = hashmap[hash]
					hashCount++
					hashmap[hash] = hashCount
				}
			}
			tot++
			str,readErr = reader.ReadBytes('\n')
			if(tot == basket){
				break
			}
		}
	}else{
		log.Fatal(inErr)
	}

	closeErr := in.Close()
	if(closeErr!=nil){
		log.Fatal(closeErr)
	}
	
	var key string
	var finalCount int
	var finalHash int
	var finalPairMap map[string]int
	finalPairMap = make(map[string]int)
	// var bitmap map[byte]int
	// bitmap = make(map[byte]int)

	bitmap := make([]byte,2500/8)

	fmt.Println("Total:",tot)
	fmt.Println("Threshold:",threshold)

	if(tot < basket || basket < 1){
		basket = tot
	}
	fmt.Println("Baskets:",basket)

	//basket number tracker
	tot = 0

	for i := range hashmap{
		bitmap[byte(i)/8] = bitmap[byte(i)/8] | (1 << (byte(i)%8))
	}

	//Deletes map
	hashmap = nil;
	//pass 2
	newin, inErr2 := os.Open(file)
	if inErr2==nil{
		newReader:=bufio.NewReader(newin)
		newStr,readErr2 := newReader.ReadBytes('\n')
		for readErr2==nil{
			//because ReadBytes returns a single string, strings.Split() is used to cut the string into a multiple strings
			newString := strings.Split(string(newStr)," ")
			for i:=0;i<len(newString)-1;i++{
				if singlemap[newString[i]]>threshold{
					for j:=i+1;j<len(newString)-1;j++{
						if singlemap[newString[i]]>threshold{
							//convert keys to int inorder to hash
							intOne, convErrOne = strconv.Atoi(newString[i])
							if convErrOne != nil{
								log.Panic(convErrOne)
							}
							intTwo, convErrTwo = strconv.Atoi(newString[j])
							if convErrOne != nil{
								log.Panic(convErrTwo)
							}
							finalHash = (intOne ^ intTwo)%2500
							if(bitmap[byte(finalHash)/8] & (1<<(byte(finalHash)%8))>0){
								//concatenate string to create key for pair
								key = newString[i]+ " "+newString[j]
								finalCount = finalPairMap[key]
								finalCount++
								finalPairMap[key] = finalCount
							}
						}
					}
				}
			}	
			newStr,readErr2 = newReader.ReadBytes('\n')
			if(readErr2 == io.EOF){
				break
			}
		}
	}else{
		log.Fatal(inErr)
	}
	closeErr2 := newin.Close()
	if(closeErr2!=nil){
		log.Fatal(closeErr2)
	}

	// output results
	for i := range finalPairMap{
		if(finalPairMap[i] > threshold){
			fmt.Printf("%-15s %d\n",i, finalPairMap[i])
		}
	}
}