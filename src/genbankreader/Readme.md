# Final assignment 1: Developing a GenBank reader #

## Special challenge of this assignment: parsing complex content ##

## Assignment details ##
Given the GenBank sequence format (assume only one DNA sequence resides in a single file 
– if there are more, ignore these and report this to the user).  
You can find some example files in the "data" folder of this NetBeans project.  
Do not remove the data from this folder -- this is where the test environment needs the test files!  
It is however also available from the download
section of this repo [example_genbank_files.zip](https://bitbucket.org/michiel_noback/javaintroprogrammingassignments/downloads/example_genbank_files.zip)

Create a parser for files in GenBank format, extracting of all Features only these fields:  

  1. DEFINITION
  2. ACCESSION
  3. Source/organism
  4. CDS/coordinates
  5. CDS/product
  6. CDS/protein_id
  7. CDS/translation
  8. Gene/coordinates
  9. Gene/gene (preferred) or Gene/locus_tag (if /gene is absent)
  10. ORIGIN (nucleotide sequence)

Create an appropriate data model that extends the code you find in this package. 
Create an executable that can be used for the listed use cases. 
For all JUnit-tested use cases, example output is shown below.  
The use cases:  

  1. ```java -jar GenBankReader --help```  
    shows informative help/usage information 
  2. ```java -jar GenBankReader.jar --infile <INFILE> --summary```  
    Creates a textual summary of the parsed file: parsed file, length of the sequence,
    for genes: count and forward/reverse balance and for CDS features: count only. 
    **NB: the F/R balance is the number on the forward strand divided by the total number**
  3. ```java -jar GenBankReader.jar --infile <INFILE> --fetch_gene <GENE NAME (-PATTERN)>```  
    Returns nucleotide sequences of the genes that match the gene name pattern, in Fasta format
  4. ```java -jar GenBankReader.jar --infile <INFILE> --fetch_cds <PRODUCT NAME (-PATTERN)>```  
    Returns the amino acid sequences of the CDSs that match the product name pattern, in Fasta format  
  5. ```java -jar GenBankReader.jar --infile <INFILE> --fetch_features <COORDINATES>```  
    Returns all features with name, type, start, stop and orientation between the given coordinates.
    Coordinates are given from..to. Only features that are completely covered on the given region should be listed.  
  6. ```java -jar GenBankReader.jar --infile <INFILE> --find_sites <DNA SEQ WITH IUPAC CODES>```  
    Lists the locations of all the sites where the DNA pattern is found: 
    position and actual sequence and (if relevant) the gene in which it resides

Use case 2 example:  
 
```
michiel@bin206: java -jar GenBankReader.jar --infile data/example_genbank_file.gb --summary  
file              example_genbank_file.gb  
organism          Saccharomyces cerevisiae  
accession         U49845  
sequence length   5028 bp  
number of genes   2  
gene F/R balance  0.5  
number of CDSs    3  
```

Use case 3 example:  
 
```
java -jar GenBankReader.jar --infile example_data/genbank_file.gb --fetch_gene AXL2  
>gene AXL2 sequence  
atgacacagcttcagatttcattattgctgacagctactatatcactactccatctagtagtggccacgccctatgaggc  
atatcctatcggaaaacaataccccccagtggcaagagtcaatgaatcgtttacatttcaaatttccaatgatacctata  
aatcgtctgtagacaagacagctcaaataacatacaattgcttcgacttaccgagctggctttcgtttgactctagttct  
agaacgttctcaggtgaaccttcttctgacttactatctgatgcgaacaccacgttgtatttcaatgtaatactcgaggg  
tacggactctgccgacagcacgtctttgaacaatacataccaatttgttgttacaaaccgtccatccatctcgctatcgt  
cagatttcaatctattggcgttgttaaaaaactatggttatactaacggcaaaaacgctctgaaactagatcctaatgaa  
gtcttcaacgtgacttttgaccgttcaatgttcactaacgaagaatccattgtgtcgtattacggacgttctcagttgta  
taatgcgccgttacccaattggctgttcttcgattctggcgagttgaagtttactgggacggcaccggtgataaactcgg  
cgattgctccagaaacaagctacagttttgtcatcatcgctacagacattgaaggattttctgccgttgaggtagaattc  
gaattagtcatcggggctcaccagttaactacctctattcaaaatagtttgataatcaacgttactgacacaggtaacgt  
ttcatatgacttacctctaaactatgtttatctcgatgacgatcctatttcttctgataaattgggttctataaacttat  
tggatgctccagactgggtggcattagataatgctaccatttccgggtctgtcccagatgaattactcggtaagaactcc  
aatcctgccaatttttctgtgtccatttatgatacttatggtgatgtgatttatttcaacttcgaagttgtctccacaac  
ggatttgtttgccattagttctcttcccaatattaacgctacaaggggtgaatggttctcctactattttttgccttctc  
agtttacagactacgtgaatacaaacgtttcattagagtttactaattcaagccaagaccatgactgggtgaaattccaa  
tcatctaatttaacattagctggagaagtgcccaagaatttcgacaagctttcattaggtttgaaagcgaaccaaggttc  
acaatctcaagagctatattttaacatcattggcatggattcaaagataactcactcaaaccacagtgcgaatgcaacgt  
ccacaagaagttctcaccactccacctcaacaagttcttacacatcttctacttacactgcaaaaatttcttctacctcc  
gctgctgctacttcttctgctccagcagcgctgccagcagccaataaaacttcatctcacaataaaaaagcagtagcaat  
tgcgtgcggtgttgctatcccattaggcgttatcctagtagctctcatttgcttcctaatattctggagacgcagaaggg  
aaaatccagacgatgaaaacttaccgcatgctattagtggacctgatttgaataatcctgcaaataaaccaaatcaagaa  
aacgctacacctttgaacaacccctttgatgatgatgcttcctcgtacgatgatacttcaatagcaagaagattggctgc  
tttgaacactttgaaattggataaccactctgccactgaatctgatatttccagcgtggatgaaaagagagattctctat  
caggtatgaatacatacaatgatcagttccaatcccaaagtaaagaagaattattagcaaaacccccagtacagcctcca  
gagagcccgttctttgacccacagaataggtcttcttctgtgtatatggatagtgaaccagcagtaaataaatcctggcg  
atatactggcaacctgtcaccagtctctgatattgtcagagacagttacggatcacaaaaaactgttgatacagaaaaac  
ttttcgatttagaagcaccagagaaggaaaaacgtacgtcaagggatgtcactatgtcttcactggacccttggaacagc  
aatattagcccttctcccgtaagaaaatcagtaacaccatcaccatataacgtaacgaagcatcgtaaccgccacttaca  
aaatattcaagactctcaaagcggtaaaaacggaatcactcccacaacaatgtcaacttcatcttctgacgattttgttc  
cggttaaagatggtgaaaatttttgctgggtccatagcatggaaccagacagaagaccaagtaagaaaaggttagtagat  
ttttcaaataagagtaatgtcaatgttggtcaagttaaggacattcacggacgcatcccagaaatgctgtga  
```
**NB: note that in GenBank format, "human" numbering is employed: position 1 is
 array position 0, and sequence end position x means "including x"!  **

**NB2: for all use cases with patterns as search string, you should list all matches
 after each other, orderd by their position in the sequence and with no empty lines in between **  

**NB3: always list sequences in 80-character lines, ending with a return and no trailing spaces**  

**NB4: of there is no /gene annotation on gene or CDS elements, you should take the /locus_tag annotation**  

Use case 4 example:  
 
```
michiel@bin206: java -jar GenBankReader.jar --infile data/example_genbank_file.gb --fetch_cds Rev7p
>CDS REV7 sequence  
MNRWVEKWLRVYLKCYINLILFYRNVYPPQSFDYTTYQSFNLPQFVPINRHPALIDYIEELILDVLSKLTHVYRFSICII  
NKKNDLCIEKYVLDFSELQHVDKDDQIITETEVFDEFRSSLNSLIMHLEKLPKVNDDTITFEAVINAIELELGHKLDRNR  
RVDSLEEKAEIERDSNWVKCQEDENLPDNNGFQPPKIKLTSLVGSDVGPLIIHQFSEKLISGDDKILNGVYSQYEEGESI  
FGSLF  
```

Use case 5 example (note that for genes, the /gene tag is listed and for CDSs the /product tag):  
 
```
michiel@bin206: java -jar GenBankReader.jar --infile data/Haloarcula_marismortui_genome.gb --fetch_features "5000..10000"  
FEATURE;TYPE;START;STOP;ORIENTATION  
rrnB0003;gene;6187;6450;F  
hypothetical protein;CDS;6187;6450;F  
cdc6b;gene;6826;8064;R  
cell division control protein 6 homolog 1;CDS;6826;8064;R  
rrnB0005;gene;9123;9221;F  
hypothetical protein;CDS;9123;9221;F  
```


Use case 6 example:  
 
```
michiel@bin206: java -jar GenBankReader.jar --infile data/example_genbank_file.gb --find_sites AAARTTT 
site search: AAARTTT (regex: AAA[AG]TTT)
POSITION;SEQUENCE;GENE  
2109;AAAATTT;AXL2  
3022;AAAATTT;AXL2  
3358;AAAATTT;REV7  
4138;AAAGTTT;INTERGENIC  
```

