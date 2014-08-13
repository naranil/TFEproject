import os 
import subprocess
import csv
# path = "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository"
path = "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository"

os.chdir( path )
retval = os.getcwd()
dirs = os.listdir( path )


dirs_results = filter(lambda x:'TestTask-Experiment_Cross_Domain' in x, dirs)
for dir in dirs_results:
	print dir

domains = ['redshirting', 'prayer-in-schools', 'homeschooling', 'single-sex-education', 'mainstreaming', 'public-private-schools']

head = "| *Measure* | *all data 10-fold CV* |"
preP1 = "| *Precision P1* |"
recP1 = "| *Recall P1* |"
preP2 = "| *Precision P2* |"
recP2 = "| *Recall P2* |"
acc = "| *Accuracy* |"
accCI = "| *95%\ CI Acc* |"
f1 = "| *Macro F1* |"
f1CI = "| *95%\ CI F1* |"


TP = 0
FN = 0
FP = 0
TN = 0

for domain in domains:
	data = []
	dirs_results_cross_domain = filter(lambda x:domain in x, dirs_results)
	print dirs_results_cross_domain[0]
	with open('/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository/'+dirs_results_cross_domain[0]+'/confusionMatrix.csv', 'rb') as csvfile:
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

# results = head + "\n" + preP1 + "\n" + preP2 + "\n" + recP1 + "\n" + recP2 + "\n" + acc + "\n" + accCI + "\n" + f1 + "\n" + f1CI
# text_result = open("MarkdownResults/result_markdown_crossdomain.txt", "w")
# text_result.write(results)
# text_result.close()
