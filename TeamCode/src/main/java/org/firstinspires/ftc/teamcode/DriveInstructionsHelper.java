package org.firstinspires.ftc.teamcode;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Created by akanksha.joshi on 31-Dec-2017.
 */

public class DriveInstructionsHelper {
    public DriveInstructionsHelper(OPModeConstants.DriveInstructions action, double value){
        this.action = action;
        this.value = value;
    }
    public OPModeConstants.DriveInstructions action;
    public double value;
}
