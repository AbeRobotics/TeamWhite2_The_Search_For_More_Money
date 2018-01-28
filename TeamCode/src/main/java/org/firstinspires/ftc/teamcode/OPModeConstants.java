package org.firstinspires.ftc.teamcode;

//import com.disnodeteam.dogecv.detectors.JewelDetector;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import java.util.EnumMap;
import java.util.LinkedList;

/**
 * Created by Akanksha.Joshi on 23-Dec-2017.
 */

public class OPModeConstants {
    /*Timer Constants for Task:
 * Read ball position - 7 sec
 * Read pictograph - 10 sec
 * Lower left arm - 12 sec
 * Remove Jewel - 15 sec
 * Return to original position - 18 sec
 * Glyph maneuver - 25 sec
 * Release glyph - 28 sec
 * Push glyph - 29 sec
 * Step back few inches - 30 sec
     */
    //Action timings
    public static double PickGlyph = 5;
    public static double ReadBallColor = 7;
    public static double ReadPictograph = 10;
    public static double LowerArm = 12;
    public static double RemoveJewel = 15;
    public static double RaiseArm = 18;
    public static double GlyphManeuver = 25;
    public static double ReleaseGlyph = 28;
    public static double PushGlyph = 29;
    private static OPModeConstants opModeConstants;
    //Jewel arm servo position for "down"
    public static double jewelArmActive = 0.32d;
    //Jewel arm servo position for "up"
    public static double jewelArmInactive = 0.00d;
    //Degrees to inch value (from conversion)
    public static double degreesToInch = 0.209d;
    //Checking if we are looking for the jewels
    public static boolean JewelDetectionDisabled = false;
    //Number of ticks the motor makes per inch
    public static final double ticksPerInch = 90.541d;
    //Gear ratio (2:1)
    public static final double gearRatio = 1.0d;
    //For debugging, make sure to set it as false in auto classes
    public static boolean DEBUG = true;
    //Degree error allowed (Plus or minus 1 degree)
    public static final double gyroThreshold = 2.0d;
    //Linked list used for drive instructions
    public LinkedList<DriveInstructionsHelper> drivePath = null;
    //Setting constants and defaults
    private OPModeConstants()
    {
        LinkedList initPair = new LinkedList<EnumMap<DriveInstructions, Integer>>();
        initPair.add(new DriveInstructionsHelper(DriveInstructions.UNKNOWN, 0d));
        setDrivePath(initPair);
        setFireSequence(FireSequence.UNKNOWN);
        setSelectedTeam(SelectedTeam.UNKNOWN);
        setGlyphClawPosition(GlyphClawPosition.UNKNOWN);
        setDetectedOrder(JewelDetector.JewelOrder.UNKNOWN);
        setCryptoLocation(RelicRecoveryVuMark.UNKNOWN);
        setOrientation(Orientation.UNKNOWN);
        setAutoSpeed(AutonomousSpeed.HIGH);
        jewelArmActive = 0.70d;
        jewelArmInactive = 0.00d;
        JewelDetectionDisabled = false;
    }
    public static OPModeConstants getInstance()
    {
        if(opModeConstants==null)
        {
            opModeConstants = new OPModeConstants();
        }
        return opModeConstants;
    }
    //Commands we can give the robot in drive path
    public enum DriveInstructions
    {
        FORWARD,
        REVERSE,
        TURN,
        UNKNOWN
    }
    //The team we are on
    public enum SelectedTeam
    {
        RED_TEAM,
        BLUE_TEAM,
        UNKNOWN
    }
    //Tells robot which direction to "shimmy" to remove the jewel
    public enum FireSequence
    {
        FORWARD,
        BACKWARD,
        UNKNOWN
    }
    //Jewel arm position
    public enum jewelKickerArmPosition
    {
        REST,
        ACTION
    }
    //Glyph grabber OPEN/CLOSE position
    public enum GlyphClawPosition
    {
        OPEN,
        CLOSE,
        UNKNOWN
    }
    //Robot position
    public enum Orientation
    {
        FRONT_FACING,
        BACK_FACING,
        UNKNOWN
    }
    //Auto speeds to choose from: SLOW = 0.5, MEDIUM = 0.75, FAST = 1.0
    public enum AutonomousSpeed
    {
        SLOW,
        MEDIUM,
        HIGH
    }
    //Private variables from enums
    private RelicRecoveryVuMark cryptoLocation;
    private JewelDetector.JewelOrder jewel_order;
    private SelectedTeam selectedTeam;
    private FireSequence fireSequence;
    private jewelKickerArmPosition jewelKickerArmPosition;
    private Orientation orientation;
    private AutonomousSpeed autoSpeed;
    private GlyphClawPosition glyphClawPosition;

    //Getters and setters for variables declared above
    public AutonomousSpeed getAutoSpeed()
    {
        return autoSpeed;
    }
    public void setAutoSpeed(AutonomousSpeed speed)
    {
        autoSpeed = speed;
    }
    public void setOrientation (Orientation orientation) {this.orientation = orientation;}
    public Orientation getOrientation(){return orientation;}
    public void setCryptoLocation(RelicRecoveryVuMark cryptoLocation) {this.cryptoLocation = cryptoLocation;}
    public GlyphClawPosition getGlyphClawPosition() { return glyphClawPosition; }
    public void setGlyphClawPosition (GlyphClawPosition glyphClawPosition) {this.glyphClawPosition = glyphClawPosition;}
    public LinkedList<DriveInstructionsHelper> getDrivePath() { return drivePath; }
    public void setDrivePath (LinkedList<DriveInstructionsHelper> drivePath) {this.drivePath = drivePath;}
    public RelicRecoveryVuMark getCryptoLocation()
    {
        return cryptoLocation;
    }
    public void setDetectedOrder (JewelDetector.JewelOrder order){
        jewel_order = order;
    }
    public JewelDetector.JewelOrder getDetectedOrder ()
    {
        return jewel_order;
    }
    public void setSelectedTeam(SelectedTeam team)
    {
        selectedTeam = team;
    }
    public SelectedTeam getSelectedTeam()
    {
        return selectedTeam;
    }
    public void setFireSequence(FireSequence sequence)
    {
        fireSequence= sequence;
    }
    public FireSequence getFireSequence()
    {
        if((getSelectedTeam()== SelectedTeam.BLUE_TEAM) &&(getDetectedOrder() == JewelDetector.JewelOrder.BLUE_RED))
            {
                setFireSequence(FireSequence.FORWARD);
            }
        if((getSelectedTeam()== SelectedTeam.BLUE_TEAM) &&(getDetectedOrder() == JewelDetector.JewelOrder.RED_BLUE))
        {
            setFireSequence(FireSequence.BACKWARD);
        }
        if((getSelectedTeam()== SelectedTeam.RED_TEAM) &&(getDetectedOrder() == JewelDetector.JewelOrder.RED_BLUE))
        {
            setFireSequence(FireSequence.FORWARD);
        }
        if((getSelectedTeam()== SelectedTeam.RED_TEAM) &&(getDetectedOrder() == JewelDetector.JewelOrder.BLUE_RED))
        {
            setFireSequence(FireSequence.BACKWARD);
        }
        return fireSequence;
    }
    public void setJewelKickerArmPosition(jewelKickerArmPosition position)
    {
        jewelKickerArmPosition = position;
    }
    public jewelKickerArmPosition getJewelKickerArmPosition()
    {
        return jewelKickerArmPosition;
    }
    public void Reset()
    {
        opModeConstants = null;
    }
}
