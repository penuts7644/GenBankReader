/*
 * Copyright (c) 2015 Wout van Helvoirt [wout.van.helvoirt@gmail.com].
 * All rights reserved.
 */

package genbankreader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wout van Helvoirt [wout.van.helvoirt@gmail.com]
 * @version 1.0.0
 */
public class GenBankFeatures {

    /**
     * @param definition is a string that contains definition of the GenBank file.
     */
    private final String definition;

    /**
     * @param accession is a string that contains the accession of the GenBank file.
     */
    private final String accession;

    /**
     * @param organism is a string that contains the organism name.
     */
    private final String organism;

    /**
     * @param cdsElements is a list with CodingSequence elements.
     */
    private final List<CodingSequence> cdsElements;

    /**
     * @param geneElements is a list with Gene elements.
     */
    private final List<Gene> geneElements;

    /**
     * @param origin is a string with the GenBank sequence.
     */
    private final String origin;

    /**
    * Constructor for a single CDS.
    * @param definition contains definition of GenBank.
    * @param accession contains accession of GenBank.
    * @param organism contains organism name.
    * @param cdsElements contains CodingSequence object.
    * @param geneElements contains Gene object.
    * @param origin contains the sequence string.
    */
    public GenBankFeatures(final String definition,
            final String accession,
            final String organism,
            final List<CodingSequence> cdsElements,
            final List<Gene> geneElements,
            final String origin) {

        this.definition = definition;
        this.accession = accession;
        this.organism = organism;
        this.cdsElements = cdsElements;
        this.geneElements = geneElements;
        this.origin = origin;
    }

    /**
    * Get definition of GenBank file.
    * @return definition.
    */
    public String getDefinition() {
        return definition;
    }

    /**
    * Get accession of GenBank file.
    * @return accession.
    */
    public String getAccession() {
        return accession;
    }

    /**
    * Get organism name.
    * @return organism.
    */
    public String getOrganism() {
        return organism;
    }

    /**
    * Get list with CodingSequence objects.
    * @return cdsElements.
    */
    public List<CodingSequence> getCdsElements() {
        return cdsElements;
    }

    /**
    * Get list with Gene objects.
    * @return geneElements.
    */
    public List<Gene> getGeneElements() {
        return geneElements;
    }

    /**
    * Get sequence of GenBank.
    * @return origin.
    */
    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "Definition: " + getDefinition() + ", Acceccion: " + getAccession() + ", Organism: " + getOrganism()
                + ", CDS Elements: " + getCdsElements() + ", Gene Elements: " + getGeneElements() + ", Origin: "
                + getOrigin();
    }

    /**
    * Get a summary of GanBank file.
    * @param fileName is a string of GenBank file name.
    * @return string containing summary information.
    */
    public String getSummary(final String fileName) {

        /* Create counter and count total forward oriented gene(s). */
        int countForward = 0;
        for (Gene i : getGeneElements()) {
            if (i.getDirection() == SequenceOrientation.FORWARD) {
                countForward++;
            }
        }

        /* Return string summary. */
        return String.format("%-20s %s%n%-20s %s%n%-20s %s%n%-20s %s%n%-20s %s%n%-20s %s%n%-20s %s",
                "file", fileName,
                "organism", getOrganism(),
                "accession", getAccession(),
                "sequence length", getOrigin().length() + " bp",
                "number of genes", getGeneElements().size(),
                "gene F/R balance", (float) countForward / getGeneElements().size(),
                "number of CDSs", getCdsElements().size());
    }

    /**
    * Get all sequences from genes that match gene regex pattern.
    * @param gene is a regex string of gene name.
    * @return string containing all found genes in fasta format.
    */
    public String fetchGene(final String gene) {

        /* Initialize stringbuilder for all found genes. */
        StringBuilder sb = new StringBuilder();

        /* Iterate gene elements, create pattern and append first line to sb. */
        for (Gene i : getGeneElements()) {

            Matcher matchGene = Pattern.compile(gene).matcher(i.getGene());
            if (matchGene.find()) {
                sb.append(">gene ".concat(i.getGene() + " sequence\n"));

                /* Get sequence of matched gene, add newlines and append to sb. */
                int lineLength = 80;
                StringBuilder geneSequence = new StringBuilder(
                        getOrigin().subSequence(i.getCoordinates().getFirst() - 1,
                        i.getCoordinates().getLast()));

                while (lineLength < geneSequence.length()) {
                    geneSequence.insert(lineLength, "\n");
                    lineLength += 81;
                }
                sb.append(geneSequence);
            }
        }

        /* When no matches, return 'nothing found' message. */
        if (sb.length() > 0) {
            return String.format("%s", sb);
        } else {
            return "Gene pattern '" + gene + "' was not found in the given GenBank.";
        }
    }

    /**
    * Get all translation sequences from CDSs that match cds regex pattern.
    * @param cds is a regex string of CDS name.
    * @return string containing all found CDSs in fasta format.
    */
    public String fetchCds(final String cds) {

        /* Initialize stringbuilder for all found CDSs. */
        StringBuilder sb = new StringBuilder();

        /* Iterate CDS elements, create pattern and append first line to sb. */
        for (CodingSequence i : getCdsElements()) {

            Matcher matchCDS = Pattern.compile(cds).matcher(i.getProduct());
            if (matchCDS.find()) {
                sb.append("\n>CDS ".concat(i.getProduct() + " sequence\n"));

                /* Get translated sequence of matched CDS, add newlines and append to sb. */
                int lineLength = 80;
                StringBuilder cdsSequence = new StringBuilder(i.getTranslation());

                while (lineLength < cdsSequence.length()) {
                    cdsSequence.insert(lineLength, "\n");
                    lineLength += 81;
                }
                sb.append(cdsSequence);
            }
        }

        /* When no matches, return 'nothing found' message. */
        if (sb.length() > 0) {
            return String.format("%s", sb.replace(0, 1, ""));
        } else {
            return "CDS pattern '" + cds + "' was not found in the given GenBank.";
        }
    }

    /**
    * Get features that lay between min and max coordinates.
    * @param maxCoordinates is a string with min and max coordinates.
    * @return string with al found features.
    * @trows NumberFormatException when maxCoordinates does not have correct format.
    */
    public String fetchFeatures(final String maxCoordinates) {

        try {
            /* Split coordinates and create Coordinates object. */
            StringBuilder sb = new StringBuilder();
            String[] coordinates = maxCoordinates.split("\\.\\.");
            Coordinates cs = new Coordinates(Integer.parseInt(coordinates[0]),
                    Integer.parseInt(coordinates[coordinates.length - 1]));

            /* For each gene element add gene features to sb if coordinates are whithin given coordinates. */
            for (Gene g : getGeneElements()) {
                if (g.getCoordinates().getFirst() > cs.getFirst()
                        && g.getCoordinates().getLast() < cs.getLast()) {

                    sb.append("\n".concat(g.getGene() + ";gene;" + g.getCoordinates().getFirst() + ";"
                            + g.getCoordinates().getLast() + ";" + g.getDirection().getType()));

                    /* If gene has been added, check for coresponding CDS element and add it to sb. */
                    for (CodingSequence c : getCdsElements()) {
                        if (g.getCoordinates().getFirst() == c.getCoordinates().getFirst()
                                && g.getCoordinates().getLast() == c.getCoordinates().getLast()) {

                            sb.append("\n".concat(c.getProduct() + ";CDS;" + c.getCoordinates().getFirst() + ";"
                                    + c.getCoordinates().getLast() + ";" + g.getDirection().getType()));
                        }
                    }
                }
            }

            /* When no matches, return 'nothing found' message. */
            if (sb.length() > 0) {
                return String.format("%s%s",
                        "FEATURE;TYPE;START;STOP;ORIENTATION",
                        sb);
            } else {
                return "No gene(s) or CDS(s) was/were found between '" + maxCoordinates + "' in the given GenBank.";
            }

        /* If maxCoordinates does not have right format, return error string. */
        } catch (NumberFormatException e) {
            return "A problem occured: '" + maxCoordinates + "' should be two integers seperated by two dots.";
        }
    }

    /**
    * Get start position sites were pattern matched.
    * @param pattern is a iupac sequence string.
    * @return string with al found sites and corresponding genes.
    */
    public String findSites(final String pattern) {

        /* Initialize HashMap and add iupac codes to them. */
        Map<String, String> iupacCodes = new HashMap<>();
        iupacCodes.put("R", "[AG]");
        iupacCodes.put("Y", "[CT]");
        iupacCodes.put("S", "[GC]");
        iupacCodes.put("W", "[AT]");
        iupacCodes.put("K", "[GT]");
        iupacCodes.put("M", "[AC]");
        iupacCodes.put("B", "[CGT]");
        iupacCodes.put("D", "[AGT]");
        iupacCodes.put("H", "[ACT]");
        iupacCodes.put("V", "[ACG]");
        iupacCodes.put("N", "[ACGT]");

        /* Remove regex related charecters from pattern. */
        String filteredPattern = pattern.replaceAll("[^\\w]*", "").toUpperCase();
        String regexPattern = "";

        /* Build own regex pattern by using the previously initialized HashMap */
        for (int i = 0; i < filteredPattern.length(); i++) {
            if (iupacCodes.containsKey(filteredPattern.substring(i, i + 1))) {
                regexPattern += iupacCodes.get(filteredPattern.substring(i, i + 1));
            } else {
                regexPattern += filteredPattern.substring(i, i + 1);
            }
        }

        /* Compile regex pattern and match pattern to origin sequence. */
        StringBuilder sb = new StringBuilder();
        Matcher matchSequence = Pattern.compile(regexPattern).matcher(getOrigin());

        while (matchSequence.find()) {
            boolean match = false;
            int startPosition = matchSequence.start();

            /* For each gene element check if match within gene and append. */
            for (Gene i : getGeneElements()) {
                if (i.getCoordinates().getFirst() < startPosition
                        && i.getCoordinates().getLast() > startPosition) {

                    match = true;
                    sb.append("\n".concat((startPosition + 1) + ";" + matchSequence.group() + ";" + i.getGene()));
                }
            }

            /* When match not within gene position, append match as intergenic. */
            if (!match) {
                sb.append("\n".concat((startPosition + 1) + ";" + matchSequence.group() + ";INTERGENIC"));
            }
        }

        /* When no matches, return 'nothing found' message. */
        if (sb.length() > 0) {
            return String.format("%s%n%s%s",
                    "site search: " + filteredPattern + " (regex: " + regexPattern + ")",
                    "POSITION;SEQUENCE;GENE",
                    sb);
        } else {
            return "The pattern '" + filteredPattern + "' did not result in matches within the given GenBank.";
        }
    }
}
