====================================================================
Command line GenBank reader
====================================================================


What does the GenBank reader do?
====================================================================
The user is able to run this Java 8 program via command line. A few
input options are available for the user:

    Default options:
        1. -h, --help, Display help for this program.
        2. -i, --infile <INFILE>, Input Genbank file to proces.

    Usecases:
        1. -s, --summary, Creates a textual summary of the parsed
		   Genbank file.
        2. -G, --fetch_gene <GENE NAME (-PATTERN)>, Returns
           nucleotide sequences of the genes that match the gene
           name regex pattern, in Fasta format.
        3. -C, --fetch_cds <PRODUCT NAME (-PATTERN)>, Returns the
           amino acid sequences of the CDSs that match the product
           name regex pattern, in Fasta format.
        4. -F, --fetch_features <COORDINATES>, Returns all features
           with name, type, start, stop and orientation between the
           given coordinates. Only features that are completely
           covered within the given region are listed.
        5. -S, --find_sites <DNA SEQ WITH IUPAC CODES>, Lists the
           locations of all the sites where the DNA pattern is
           found: position, actual sequence and (if relevant) the
           gene in which it resides.

NOTE 1: -i option and one of the usecases (-s, -C, -G, -F, -S) are
        required.

NOTE 2: The GenBank reader assumes that only one DNA sequence
        resides within a single GenBank file. If multi-GenBank file
        is given, only the first item in the GenBank file will be
        processed.

How to run the file?
====================================================================

To run the project from the command line, go to the dist folder and
type the following:

java -jar "GenBankReader.jar"

The Line above will should be followed by at least one GenBank
file (-i) and one of the five usecases (-s, -C, -G, -F, -S).
Usecases 2, 3, 4 and 5 need an argument.
