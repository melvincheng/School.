package main;

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
			apriori(threshold, usrin[2], basket)
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

func apriori(threshold int, file string, basket int){
	out,err := os.Open(file)
	reader := bufio.NewReader(out)

	//occurrence count intialized
	var count int

	//basket number tracker
	var tot int

	var singlemap map[string]int
	singlemap = make(map[string]int)

	//pass 1
	if err == nil{
		str,readerr:=reader.ReadBytes('\n')
		for readerr==nil{
			//because ReadBytes returns a single string, strings.Split() is used to cut the string into a multiple strings
			newString := strings.Split(string(str)," ")
			for i:=0;i<len(newString)-1;i++{
				count = singlemap[newString[i]]
				count++
				singlemap[newString[i]] = count
			}
			tot++
			str,readerr = reader.ReadBytes('\n')
			if(tot == basket){
				break
			}
		}
	}

	closeErr := out.Close()
	if(closeErr!=nil){
		log.Fatal(closeErr)
	}
	//re-reads the file
	newout,err := os.Open(file)
	if err==nil{
		reader.Reset(bufio.NewReader(newout))
	}else{
		log.Fatal(err)
	}
	
	//creates list to store pairs greater than threshold
	var finallist []string
	var key string

	//creates map to count pair occurrences
	var pairMap map[string]int
	pairMap = make(map[string]int)

	fmt.Println("Total:",tot)
	// threshold := thresholdPercent*tot
	// threshold := thresholdPercent * 1000
	// threshold := tot*thresholdPercent*.01
	// thresholdi := int(threshold)
	fmt.Println("Threshold:",threshold)
	// fmt.Println("Threshold percentage:",thresholdPercent)
	// fmt.Printf("Threshold:%.0f\n",threshold-0.5)

	if(tot < basket || basket < 1){
		basket = tot
	}
	fmt.Println("Baskets:",basket)

	//basket number tracker
	tot = 0

	//pass 2
	str,readerr := reader.ReadBytes('\n')
	if err==nil{
		for readerr==nil{
			//because ReadBytes returns a single string, strings.Split() is used to cut the string into a multiple strings
			newString := strings.Split(string(str)," ")
			for i:=0;i<len(newString)-1;i++{
				if singlemap[newString[i]]>threshold{
					for k:=i+1;k<len(newString)-1;k++{
						if singlemap[newString[k]]>threshold{
							//concatenate string to create key for pair
							key = newString[i]+ " "+newString[k]
							count = pairMap[key]
							count++
							pairMap[key] = count
							if count==threshold{
								finallist = append(finallist, key)
							}
						}
					}
				}
			}
			tot++
			str,readerr = reader.ReadBytes('\n')
			if(readerr == io.EOF || tot == basket){
				break
			}
		}
	}

	closeErr2 := newout.Close()
	if(closeErr2!=nil){
		log.Fatal(closeErr2)
	}
	// output results
	for i:=0;i<len(finallist);i++{
		fmt.Printf("%-15s %d\n",finallist[i], pairMap[finallist[i]])
	}
}