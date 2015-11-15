/*
 * Copyright (c) 2015 Wout van Helvoirt [wout.van.helvoirt@gmail.com].
 * All rights reserved.
 */

package genbankreader;

/**
 * @author Wout van Helvoirt [wout.van.helvoirt@gmail.com]
 * @version 0.0.1
 */
public enum SequenceOrientation {

    /**
     * Forward is a sequence orientation.
     */
    FORWARD("Forward"),

    /**
     * Reverse is a sequence orientation.
     */
    REVERSE("Reverse");

    /**
     * @param type is a string containing orientation type.
     */
    private final String type;

    /**
    * Constructor for orientation type.
    * @param type contains orientation type.
    */
    private SequenceOrientation(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Orientation: " + getType();
    }
}