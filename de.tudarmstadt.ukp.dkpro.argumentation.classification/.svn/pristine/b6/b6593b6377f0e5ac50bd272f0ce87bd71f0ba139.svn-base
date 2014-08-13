import subprocess
import os
import csv

### Evaluate the statistics for the different domains
### The directories should be adapted

# Enter your own working directory
os.chdir("/home/ukp-hiwi/workspace/de.tudarmstadt.ukp.dkpro.argumentation.classification/src/main/resources/gold_data")

# Enter the directory containing P1 files
list_file_P1 = os.listdir("/home/ukp-hiwi/workspace/de.tudarmstadt.ukp.dkpro.argumentation.classification/src/main/resources/gold_data/P1")
redshirting_P1 = len(filter(lambda x:'redshirting' in x, list_file_P1))
prayerinschools_P1 = len(filter(lambda x:'prayer-in-schools' in x, list_file_P1))
homeschooling_P1 = len(filter(lambda x:'homeschooling' in x, list_file_P1))
sseducation_P1 = len(filter(lambda x:'single-sex-education' in x, list_file_P1))
mainstreaming_P1 = len(filter(lambda x:'mainstreaming' in x, list_file_P1))
ppschools_P1 = len(filter(lambda x:'public-private-schools' in x, list_file_P1))

# Enter the directory containing P2 files
list_file_P2 = os.listdir("/home/ukp-hiwi/workspace/de.tudarmstadt.ukp.dkpro.argumentation.classification/src/main/resources/gold_data/P2")
redshirting_P2 = len(filter(lambda x:'redshirting' in x, list_file_P2))
prayerinschools_P2 = len(filter(lambda x:'prayer-in-schools' in x, list_file_P2))
homeschooling_P2 = len(filter(lambda x:'homeschooling' in x, list_file_P2))
sseducation_P2 = len(filter(lambda x:'single-sex-education' in x, list_file_P2))
mainstreaming_P2 = len(filter(lambda x:'mainstreaming' in x, list_file_P2))
ppschools_P2 = len(filter(lambda x:'public-private-schools' in x, list_file_P2))

print "redshirting: P1 = "+str(redshirting_P1)+", P2 = "+str(redshirting_P2)+", Total = "+str(redshirting_P1+ redshirting_P2)
print "prayer-in-schools: P1 = "+str(prayerinschools_P1)+", P2 = "+str(prayerinschools_P2)+", Total = "+str(prayerinschools_P1+ prayerinschools_P2)
print "homeschooling: P1 = "+str(homeschooling_P1)+", P2 = "+str(homeschooling_P2)+", Total = "+str(homeschooling_P1+ homeschooling_P2)
print "single-sex-education: P1 = "+str(sseducation_P1)+", P2 = "+str(sseducation_P2)+", Total = "+str(sseducation_P1+ sseducation_P2)
print "mainstreaming: P1 = "+str(mainstreaming_P1)+", P2 = "+str(mainstreaming_P2)+", Total = "+str(mainstreaming_P1+ mainstreaming_P2)
print "public-private-schools: P1 = "+str(ppschools_P1)+", P2 = "+str(ppschools_P2)+", Total = "+str(ppschools_P1+ ppschools_P2)


