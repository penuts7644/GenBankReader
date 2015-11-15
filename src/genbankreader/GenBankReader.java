/*
 * Copyright (c) 2015 Wout van Helvoirt [wout.van.helvoirt@gmail.com].
 * All rights reserved.
 */

package genbankreader;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Wout van Helvoirt [wout.van.helvoirt@gmail.com]
 * @version 0.0.1
 */
public final class GenBankReader {

    /**
     * Main function for running program.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        GenBankReader mainObject = new GenBankReader();
        mainObject.start(args);
    }

    /**
     * private constructor.
     */
    private GenBankReader() { }

    /**
     * starts the application.
     * @param args the command line arguments passed from main()
     */
    private void start(final String[] args) {

        /* Initialize argument options and retrieve input from user. */
        ArgumentParser arguments = new ArgumentParser(args);
        List parsedArguments = arguments.parseArguments();

        /* Try to initialize GeneBankFileParser and catch exception if inputfile is not valid. */
        try {
            GeneBankFileParser infile = new GeneBankFileParser((String) parsedArguments.get(0));
            GenBankFeatures gbk = infile.ParseGenBankContent();

            /* Use switch to give user correct output. */
            switch (parsedArguments.get(1).toString()) {
                case "summary": System.out.println(gbk.getSummary(infile.getInputName())); break;
                case "fetch_gene": System.out.println(gbk.fetchGene(parsedArguments.get(2).toString())); break;
                case "fetch_cds": System.out.println(gbk.fetchCds(parsedArguments.get(2).toString())); break;
                case "fetch_features": System.out.println(gbk.fetchFeatures(parsedArguments.get(2).toString())); break;
                case "find_sites": System.out.println(gbk.findSites(parsedArguments.get(2).toString())); break;
                default: arguments.help();
            }

        } catch (FileNotFoundException e) {
            System.out.println("A problem occured: " + e + "\n");
        }
    }
}