# Command line GenBank reader #

## What does the GenBank reader do? ##

The user is able to run this Java 8 program via command line. A few input options are available for the user:

### Default options: ###

  1. -h, --help, Display help for this program.
  2. -i, --infile, Input Genbank file to proces.


### Usecases: ###

  1. -s, --summary, Creates a textual summary of the parsed Genbank file.
  2. -G, --fetch_gene \<GENE NAME (-PATTERN)>, Returns nucleotide sequences of the genes that match the gene name regex pattern, in Fasta format.
  3. -C, --fetch_cds \<PRODUCT NAME (-PATTERN)>, Returns the amino acid sequences of the CDSs that match the product name regex pattern, in Fasta format.
  4. -F, --fetch_features \<COORDINATES>, Returns all features with name, type, start, stop and orientation between the given coordinates. Only features that are completely covered within the given region are listed.
  5. -S, --find_sites \<DNA SEQ WITH IUPAC CODES>, Lists the locations of all the sites where the DNA pattern is found: position, actual sequence and (if relevant) the gene in which it resides.

** Note 1: -i option and one of the usecases (-s, -C, -G, -F, -S) are required. **

** Note 2: The GenBank reader assumes that only one DNA sequence resides within a single GenBank file. If multi-GenBank file is given, only the first item in the GenBank file will be processed. **

Below are examples for each use case. The example GenBank files used, can be found in the 'data' folder within this repository.  

Use case 1 example:  
 
```
demo@pc: java -jar GenBankReader.jar -i data/example_genbank_file.gb -s
file                 example_genbank_file.gb
organism             Saccharomyces cerevisiae
accession            U49845
sequence length      5028 bp
number of genes      2
gene F/R balance     0.5
number of CDSs       3
```

Use case 2 example:  
 
```
demo@pc: java -jar GenBankReader.jar -i example_data/genbank_file.gb -G REV7 
>gene REV7 sequence
TTAAAACAAAGATCCAAAAATGCTCTCGCCCTCTTCATATTGAGAATACACTCCATTCAAAATTTTGTCGTCACCGCTGA
TTAATTTTTCACTAAACTGATGAATAATCAAAGGCCCCACGTCAGAACCGACTAAAGAAGTGAGTTTTATTTTAGGAGGT
TGAAAACCATTATTGTCTGGTAAATTTTCATCTTCTTGACATTTAACCCAGTTTGAATCCCTTTCAATTTCTGCTTTTTC
CTCCAAACTATCGACCCTCCTGTTTCTGTCCAACTTATGTCCTAGTTCCAATTCGATCGCATTAATAACTGCTTCAAATG
TTATTGTGTCATCGTTGACTTTAGGTAATTTCTCCAAATGCATAATCAAACTATTTAAGGAAGATCGGAATTCGTCGAAC
ACTTCAGTTTCCGTAATGATCTGATCGTCTTTATCCACATGTTGTAATTCACTAAAATCTAAAACGTATTTTTCAATGCA
TAAATCGTTCTTTTTATTAATAATGCAGATGGAAAATCTGTAAACGTGCGTTAATTTAGAAAGAACATCCAGTATAAGTT
CTTCTATATAGTCAATTAAAGCAGGATGCCTATTAATGGGAACGAACTGCGGCAAGTTGAATGACTGGTAAGTAGTGTAG
TCGAATGACTGAGGTGGGTATACATTTCTATAAAATAAAATCAAATTAATGTAGCATTTTAAGTATACCCTCAGCCACTT
CTCTACCCATCTATTCAT
```

Use case 3 example:  
 
```
demo@pc: java -jar GenBankReader.jar -i example_data/genbank_file.gb -C [AR].* 
>CDS Axl2p sequence
MTQLQISLLLTATISLLHLVVATPYEAYPIGKQYPPVARVNESFTFQISNDTYKSSVDKTAQITYNCFDLPSWLSFDSSS
RTFSGEPSSDLLSDANTTLYFNVILEGTDSADSTSLNNTYQFVVTNRPSISLSSDFNLLALLKNYGYTNGKNALKLDPNE
VFNVTFDRSMFTNEESIVSYYGRSQLYNAPLPNWLFFDSGELKFTGTAPVINSAIAPETSYSFVIIATDIEGFSAVEVEF
ELVIGAHQLTTSIQNSLIINVTDTGNVSYDLPLNYVYLDDDPISSDKLGSINLLDAPDWVALDNATISGSVPDELLGKNS
NPANFSVSIYDTYGDVIYFNFEVVSTTDLFAISSLPNINATRGEWFSYYFLPSQFTDYVNTNVSLEFTNSSQDHDWVKFQ
SSNLTLAGEVPKNFDKLSLGLKANQGSQSQELYFNIIGMDSKITHSNHSANATSTRSSHHSTSTSSYTSSTYTAKISSTS
AAATSSAPAALPAANKTSSHNKKAVAIACGVAIPLGVILVALICFLIFWRRRRENPDDENLPHAISGPDLNNPANKPNQE
NATPLNNPFDDDASSYDDTSIARRLAALNTLKLDNHSATESDISSVDEKRDSLSGMNTYNDQFQSQSKEELLAKPPVQPP
ESPFFDPQNRSSSVYMDSEPAVNKSWRYTGNLSPVSDIVRDSYGSQKTVDTEKLFDLEAPEKEKRTSRDVTMSSLDPWNS
NISPSPVRKSVTPSPYNVTKHRNRHLQNIQDSQSGKNGITPTTMSTSSSDDFVPVKDGENFCWVHSMEPDRRPSKKRLVD
FSNKSNVNVGQVKDIHGRIPEML
>CDS Rev7p sequence
MNRWVEKWLRVYLKCYINLILFYRNVYPPQSFDYTTYQSFNLPQFVPINRHPALIDYIEELILDVLSKLTHVYRFSICII
NKKNDLCIEKYVLDFSELQHVDKDDQIITETEVFDEFRSSLNSLIMHLEKLPKVNDDTITFEAVINAIELELGHKLDRNR
RVDSLEEKAEIERDSNWVKCQEDENLPDNNGFQPPKIKLTSLVGSDVGPLIIHQFSEKLISGDDKILNGVYSQYEEGESI
FGSLF
```

Use case 4 example (the /gene tag is listed for genes and the /product tag for CDS's):  
 
```
demo@pc: java -jar GenBankReader.jar -i example_data/genbank_file.gb -F 500..5000
FEATURE;TYPE;START;STOP;ORIENTATION
AXL2;gene;687;3158;Forward
Axl2p;CDS;687;3158;Forward
REV7;gene;3300;4037;Reverse
Rev7p;CDS;3300;4037;Reverse
```

Use case 5 example:  
 
```
demo@pc: java -jar GenBankReader.jar -i example_data/genbank_file.gb -S aaarttt
site search: AAARTTT (regex: AAA[AG]TTT)
POSITION;SEQUENCE;GENE
2109;AAAATTT;AXL2
3022;AAAATTT;AXL2
3358;AAAATTT;REV7
4138;AAAGTTT;INTERGENIC
```

