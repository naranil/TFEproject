import os

pos = ['CC', 'CD', 'DT', 'EX', 'FW', 'IN', 'JJ', 'JJR', 'JJS', 'LS', 'MD', 'NN', 'NNS', 'NNP', 'NNPS', 'PDT', 'POS', 'PRP', 'PRP$', 'RB', 'RBR', 'RBS', 'RP', 'SYM', 'TO', 'UH', 'VB', 'VBD', 'VBG', 'VBN', 'VBP', 'VBZ', 'WDT', 'WP', 'WP$', 'WRB']
os.chdir("/home/ukp-hiwi/workspace/de.tudarmstadt.ukp.dkpro.argumentation.classification/src/main/resources/info") 
text_file = open("pos2grams.txt", "w")

for first in pos:
	for second in pos:
		str = first + "_" + second + "\n"
		text_file.write(str)

text_file.close()