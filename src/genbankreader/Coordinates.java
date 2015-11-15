/*
 * Copyright (c) 2015 Wout van Helvoirt [wout.van.helvoirt@gmail.com].
 * All rights reserved.
 */

package genbankreader;

/**
 * @author Wout van Helvoirt [wout.van.helvoirt@gmail.com]
 * @version 0.0.1
 */
public class Coordinates {

    /**
     * @param first is a integer start coordinate.
     */
    private final int first;

    /**
     * @param last is a integer stop coordinate.
     */
    private final int last;

    /**
    * Constructor creating the start and stop coordinates.
    * @param first contains start coordinate.
    * @param last contains stop coordinate.
    */
    public Coordinates(final int first,
            final int last) {

        this.first = first;
        this.last = last;
    }

    /**
    * Get start coordinate value.
    * @return first.
    */
    public int getFirst() {
        return first;
    }

    /**
    * Get stop coordinate value.
    * @return stop.
    */
    public int getLast() {
        return last;
    }

    @Override
    public String toString() {
        return "Start: " + getFirst() + ", Stop: " + getLast();
    }
}
