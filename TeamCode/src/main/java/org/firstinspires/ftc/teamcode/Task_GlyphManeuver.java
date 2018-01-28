package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by akanksha.joshi on 31-Dec-2017.
 */

public class Task_GlyphManeuver extends IOPModeTaskBase {
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;
    private OPModeGyroHelper gyroHelper;
    LinkedList<DriveInstructionsHelper> drivePath = null;
    public Task_GlyphManeuver(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {

        OPModeDriveHelper driveHelper = OPModeDriveHelper.getInstance();
        driveHelper.Init(telemetry, hardwareMap);
        // more than 2 tasks are for putting glyph in cryptobox - called by glyph maneuver
        if (drivePath.size() > 2 && elapsedTime > OPModeConstants.GlyphManeuver && !OPModeConstants.DEBUG) {
            taskSatisfied = true;
            driveHelper.SetAllStop();
            driveHelper.ResetDriveEncoders();
            return;
        }
        // Upgrade if time permits
        //// Less than 3 task are for jewel removal - we turn and go back to original
        // call glyph maneuver in jewel removal instead of directly using drive helper
        if (drivePath.size() < 3 && elapsedTime > OPModeConstants.PushGlyph && !OPModeConstants.DEBUG) {
            taskSatisfied = true;
            return;
        }
        boolean availableTask = true;


        while (availableTask){
            DriveInstructionsHelper firstInstruction = drivePath.peekFirst();
            if (firstInstruction == null){
                availableTask = false;
            }
            else{
                OPModeConstants.DriveInstructions action = firstInstruction.action;
                double value = firstInstruction.value;
                switch (action){
                    case FORWARD:
                        driveHelper.MoveForward(value);
                        telemetry.addData("Action = ", action.toString());
                        telemetry.update();
                        break;
                    case REVERSE:
                        driveHelper.MoveBackward(value);
                        break;
                    case TURN:
                        gyroHelper.Init(telemetry,hardwareMap);
                        //double currentPosition = gyroHelper.GetGyroAngle();
                        driveHelper.Turn((int)value, OPModeConstants.AutonomousSpeed.MEDIUM);
                        //driveHelper.gyroTurn( 0.5,normalizeAngle(currentPosition+value));
                        break;
                    case UNKNOWN:
                        driveHelper.ResetDriveEncoders();
                        driveHelper.SetAllStop();
                        break;
                    default:
                        driveHelper.ResetDriveEncoders();
                        driveHelper.SetAllStop();
                        break;
                }
                drivePath.remove(firstInstruction);
            }
        }
        taskSatisfied = true;
        driveHelper.SetAllStop();
        driveHelper.ResetDriveEncoders();
    }

    @Override
    public void Init() {
        opModeConstants = OPModeConstants.getInstance();
        drivePath = opModeConstants.getDrivePath();
        gyroHelper = OPModeGyroHelper.getInstance();
    }

    @Override
    public void Reset() {
        Map initEnumMap = new EnumMap<OPModeConstants.DriveInstructions, Integer>(OPModeConstants.DriveInstructions.class);
        initEnumMap.put(OPModeConstants.DriveInstructions.UNKNOWN, 0);
        LinkedList initPair = new LinkedList<EnumMap<OPModeConstants.DriveInstructions, Integer>>();
        initPair.add(initEnumMap);
        opModeConstants.setDrivePath(initPair);
        taskSatisfied = false;
    }
    private double normalizeAngle(double angle)
    {
        double newAngle = angle;
        while (newAngle <= -180) newAngle += 360;
        while (newAngle > 180) newAngle -= 360;
        return newAngle;
    }
}
