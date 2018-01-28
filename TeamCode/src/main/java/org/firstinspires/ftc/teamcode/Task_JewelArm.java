package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by akanksha.joshi on 25-Dec-2017.
 */

public class Task_JewelArm extends IOPModeTaskBase {

    private Servo leftArm = null;
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;
    private OPModeConstants.jewelKickerArmPosition position;
    public Task_JewelArm(HardwareMap hardwareMap, OPModeConstants.jewelKickerArmPosition position)
    {
        this.hardwareMap = hardwareMap;
        taskSatisfied = false;
        this.position = position;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        if ((elapsedTime > OPModeConstants.RaiseArm) && (position == OPModeConstants.jewelKickerArmPosition.ACTION) && !OPModeConstants.DEBUG) {
            taskSatisfied = true;
            return;
        }
        if ((elapsedTime > OPModeConstants.LowerArm) && (position == OPModeConstants.jewelKickerArmPosition.REST)&& !OPModeConstants.DEBUG) {
            taskSatisfied = true;
            return;
        }
        if(position == OPModeConstants.jewelKickerArmPosition.ACTION)
        {
            leftArm.setPosition(OPModeConstants.jewelArmActive);
        }
        if(position == OPModeConstants.jewelKickerArmPosition.REST)
        {
            leftArm.setPosition(OPModeConstants.jewelArmInactive);
        }
        taskSatisfied = true;
    }

    @Override
    public void Init() {
        leftArm = hardwareMap.servo.get("left_arm");
    }

    @Override
    public void Reset() {
        taskSatisfied = false;
    }
}
