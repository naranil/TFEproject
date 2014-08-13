import os 
import subprocess
import csv
# path = "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository"
path = "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository"

os.chdir( path )
retval = os.getcwd()
dirs = os.listdir( path )


dirs_results = filter(lambda x:'Evaluation-Experiment' in x, dirs)


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


## Create a directory with the Markdown results
pathResults= "/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository/MarkdownResults"
os.mkdir( pathResults, 0755 );

### FULL CV ###
dirs_results_full = filter(lambda x:'Full' in x, dirs_results)

with open(path + '/'
	+dirs_results_full[0]+'/result_all.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
		for row in spamreader:
			if row[0] == 'Precision P1':
				preP1 += str(row[1]) + ' |'
			if row[0] == 'Recall P1':
				recP1 += str(row[1]) + ' |'
			if row[0] == 'Precision P2':
				preP2 += str(row[1]) + ' |'
			if row[0] == 'Recall P2':
				recP2 += str(row[1]) + ' |'
			if row[0] == 'Accuracy':
				acc += str(row[1]) + ' |'
			if row[0] == '95_CI_Acc':
				accCI += str(row[1]) + ' |'
			if row[0] == 'Macro F1':
				f1 += str(row[1]) + ' |'
			if row[0] == '95_CI_F1':
				f1CI += str(row[1]) + ' |'
results = head + "\n" + preP1 + "\n" + preP2 + "\n" + recP1 + "\n" + recP2 + "\n" + acc + "\n" + accCI + "\n" + f1 + "\n" + f1CI

text_result = open("MarkdownResults/result_markdown_fullcv.txt", "w")
text_result.write(results)
text_result.close()

head = "| *Measure* | *redshirting* | *prayer-in-schools* | *homeschooling* | *single-sex-education* | *mainstreaming* | *public-private-schools* |"
preP1 = "| *Precision P1* |"
recP1 = "| *Recall P1* |"
preP2 = "| *Precision P2* |"
recP2 = "| *Recall P2* |"
acc = "| *Accuracy* |"
accCI = "| *95%\ CI Acc* |"
f1 = "| *Macro F1* |"
f1CI = "| *95%\ CI F1* |"

### IN DOMAIN CV ###

dirs_results_in_domain = filter(lambda x:'Evaluation-Experiment_Cross_Validation_In_Domain_' in x, dirs_results)

for domain in domains:
	dirs_results_in_domain = filter(lambda x:domain in x, dirs_results)
	if "Cross_Validation_In_Domain" in dirs_results_in_domain[0]:
		dirs_results_in_domain_path = dirs_results_in_domain[0]
	else:
		dirs_results_in_domain_path = dirs_results_in_domain[1]

	with open(path + '/'
		+dirs_results_in_domain_path+'/result_all.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
		for row in spamreader:
			if row[0] == 'Precision P1':
				preP1 += str(row[1]) + ' |'
			if row[0] == 'Recall P1':
				recP1 += str(row[1]) + ' |'
			if row[0] == 'Precision P2':
				preP2 += str(row[1]) + ' |'
			if row[0] == 'Recall P2':
				recP2 += str(row[1]) + ' |'
			if row[0] == 'Accuracy':
				acc += str(row[1]) + ' |'
			if row[0] == '95_CI_Acc':
				accCI += str(row[1]) + ' |'
			if row[0] == 'Macro F1':
				f1 += str(row[1]) + ' |'
			if row[0] == '95_CI_F1':
				f1CI += str(row[1]) + ' |'

results = head + "\n" + preP1 + "\n" + preP2 + "\n" + recP1 + "\n" + recP2 + "\n" + acc + "\n" + accCI + "\n" + f1 + "\n" + f1CI
text_result = open("MarkdownResults/result_markdown_indomain.txt", "w")
text_result.write(results)
text_result.close()

### CROSS DOMAIN CV ###

head = "| *Measure* | *redshirting* | *prayer-in-schools* | *homeschooling* | *single-sex-education* | *mainstreaming* | *public-private-schools* |"
preP1 = "| *Precision P1* |"
recP1 = "| *Recall P1* |"
preP2 = "| *Precision P2* |"
recP2 = "| *Recall P2* |"
acc = "| *Accuracy* |"
accCI = "| *95% CI Acc* |"
f1 = "| *Macro F1* |"
f1CI = "| *95% CI F1* |"

dirs_results_cross_domain = filter(lambda x:'Evaluation-Experiment_Cross_Domain_' in x, dirs_results)

for domain in domains:
	dirs_results_cross_domain = filter(lambda x:domain in x, dirs_results)
	if "Cross_Domain" in dirs_results_cross_domain[0]:
		dirs_results_cross_domain_path = dirs_results_cross_domain[0]
	else:
		dirs_results_cross_domain_path = dirs_results_cross_domain[1]


	with open(path + '/'
		+dirs_results_cross_domain_path+'/result_all.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
		for row in spamreader:
			if row[0] == 'Precision P1':
				preP1 += str(row[1]) + ' |'
			if row[0] == 'Recall P1':
				recP1 += str(row[1]) + ' |'
			if row[0] == 'Precision P2':
				preP2 += str(row[1]) + ' |'
			if row[0] == 'Recall P2':
				recP2 += str(row[1]) + ' |'
			if row[0] == 'Accuracy':
				acc += str(row[1]) + ' |'
			if row[0] == '95_CI_Acc':
				accCI += str(row[1]) + ' |'
			if row[0] == 'Macro F1':
				f1 += str(row[1]) + ' |'
			if row[0] == '95_CI_F1':
				f1CI += str(row[1]) + ' |'

results = head + "\n" + preP1 + "\n" + preP2 + "\n" + recP1 + "\n" + recP2 + "\n" + acc + "\n" + accCI + "\n" + f1 + "\n" + f1CI
text_result = open("MarkdownResults/result_markdown_crossdomain.txt", "w")
text_result.write(results)
text_result.close()

### FULL CROSS DOMAIN CV ###

head = "| *Measure* | *all data 10-fold Cross Dromain* |"
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
	dirs_mat = filter(lambda x:'TestTask-Experiment_Cross_Domain' + "_" + domain in x, dirs)
	with open('/home/ukp-hiwi/workspace/dkprohome/de.tudarmstadt.ukp.dkpro.lab/repository/'+dirs_mat[0]+'/confusionMatrix.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=',', quotechar='"')
		for row in spamreader:
			data.append(row)
		TP += float(data[1][1])
		FN += float(data[1][2])
		FP += float(data[2][1])
		TN += float(data[2][2])	

print "TP = " + str(TP) + "\n" + "FN = " + str(FN) + "\n" + "FP = " + str(FP) + "\n" + "TN = " + str(TN)
total = TP + FN + FP + TN
accuracy = (TP + TN) / (TP + TN + FP + FN)
fmes = (2*(TP / (TP + FP))*(TP / (TP + FN))) / ((TP / (TP + FP)) + (TP / (TP + FN)))
confidence95Accuracy = 1.96 * (accuracy * (1 - accuracy)/total)**0.5
confidence95fmes = 1.96 * (fmes * (1 - fmes)/total)**0.5

preP1 += str(round(TP / (TP + FP), 4)) + ' |'
recP1 += str(round(TP / (TP + FN), 4)) + ' |'
preP2 += str(round(TN / (TN + FN), 4)) + ' |'
recP2 += str(round(TN / (TN + FP), 4)) + ' |'
acc += str(round((TP + TN) / (TP + TN + FP + FN), 4)) + ' |'
accCI += str(round(accuracy - confidence95Accuracy, 4)) + "-" + str(round(accuracy + confidence95Accuracy, 4)) + ' |'
f1 += str(round((2*(TP / (TP + FP))*(TP / (TP + FN))) / ((TP / (TP + FP)) + (TP / (TP + FN))), 4)) + ' |'
f1CI += str(round(fmes - confidence95fmes, 4)) + "-" + str(round(fmes + confidence95fmes, 4)) + ' |'

results = head + "\n" + preP1 + "\n" + preP2 + "\n" + recP1 + "\n" + recP2 + "\n" + acc + "\n" + accCI + "\n" + f1 + "\n" + f1CI

print results
text_result = open("MarkdownResults/result_markdown_full_crossdomain.txt", "w")
text_result.write(results)
text_result.close()
