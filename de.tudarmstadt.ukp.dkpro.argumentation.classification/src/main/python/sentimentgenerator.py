import os 

path = "/home/ukp-hiwi/workspace/de.tudarmstadt.ukp.dkpro.argumentation.classification/src/main/resources/sentimentfluctuation"

os.chdir( path )
retval = os.getcwd()
dirs = os.listdir( path )



sentiment = ["--", "-", "0", "+", "++"]
rules = ""

for sen1 in sentiment:
	for sen2 in sentiment:
		rules += sen1 + " -> " + sen2 + "\n"

print rules


	