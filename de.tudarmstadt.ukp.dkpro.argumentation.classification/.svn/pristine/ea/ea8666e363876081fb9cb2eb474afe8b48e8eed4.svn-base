import subprocess
import os
import csv

os.chdir("/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository")
list_file = os.listdir("/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository")
filterlist = filter(lambda x:'TestTask' in x, list_file)

TP = 0
FN = 0
FP = 0
TN = 0

for folder in filterlist:
	data = []
	with open('/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository/'+folder+'/confusionMatrix.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
		for row in spamreader:
			data.append(row)
		TP += float(data[1][1])
		FN += float(data[1][2])
		FP += float(data[2][1])
		TN += float(data[2][2])		

Precision = TP / (TP + FP)	
Recall = TP / (TP  + FN)
Accuracy = (TP + TN) / (TP + TN + FP + FN)
F = 2 * (Precision * Recall) / (Precision + Recall)
print "True positive: " + str(TP)
print "True negative: " + str(TN)
print "False positive: " + str(FP)
print "False negative: " + str(FN)
print "Precision: " + str(Precision)
print "Recall: " + str(Recall)
print "Accuracy: " + str(Accuracy)
print "F measure: " + str(F)

