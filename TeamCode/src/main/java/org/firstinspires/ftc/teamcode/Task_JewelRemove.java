package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Akanksha.Joshi on 25-Dec-2017.
 */

public class Task_JewelRemove extends IOPModeTaskBase {
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private JewelDriveMode jewelDriveMode = null;
    private HardwareMap hardwareMap = null;

    public Task_JewelRemove(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
        taskSatisfied = false;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }


    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        if (elapsedTime > OPModeConstants.RemoveJewel && !OPModeConstants.DEBUG) {
            taskSatisfied = true;
            return;
        }
        OPModeConstants.FireSequence fireSequence = opModeConstants.getFireSequence();
        if( fireSequence != OPModeConstants.FireSequence.UNKNOWN)
        {
            jewelDriveMode.performJewelRemovalTask(fireSequence,hardwareMap,telemetry);
            taskSatisfied = true;
        }


    }

    @Override
    public void Init() {

        jewelDriveMode = JewelDriveMode.jewelDriveModeInstance();
        opModeConstants = OPModeConstants.getInstance();
    }

    @Override
    public void Reset() {
        taskSatisfied = false;
        opModeConstants.Reset();
        jewelDriveMode.Reset();
    }
}
