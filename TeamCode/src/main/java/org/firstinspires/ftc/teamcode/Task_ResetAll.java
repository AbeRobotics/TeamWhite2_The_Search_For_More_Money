package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by akanksha.joshi on 2018-01-06.
 * Reset the shit out of everything
 * F***ing encoders not getting reset - this is the last ditch effort!!
 */

public class Task_ResetAll extends IOPModeTaskBase {
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }
    public Task_ResetAll(HardwareMap hardwareMap)
    {
        taskSatisfied = false;
        this.hardwareMap = hardwareMap;
    }
    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        opModeConstants.Reset();
        JewelDetectorFacade.getInstance().Reset();
        JewelDriveMode.jewelDriveModeInstance().Reset();

        taskSatisfied = true;
    }

    @Override
    public void Init() {
        opModeConstants = OPModeConstants.getInstance();
    }

    @Override
    public void Reset() {

    }
}
