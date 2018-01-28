package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by akanksha.joshi on 2018-01-01.
 */

public class Task_LowerGlyph extends IOPModeTaskBase {
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;
    DcMotor liftMotor = null;

    public Task_LowerGlyph(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        if (elapsedTime > OPModeConstants.ReleaseGlyph && !OPModeConstants.DEBUG ) {
            taskSatisfied = true;
            return;
        }
        if(taskSatisfied==false) {
            liftMotor.setPower(1.0);
            sleep(1000);
            liftMotor.setPower(0);
            taskSatisfied = true;
        }
    }

    @Override
    public void Init() {
        liftMotor = hardwareMap.dcMotor.get("lift_motor");
        opModeConstants = OPModeConstants.getInstance();
    }

    @Override
    public void Reset() {
        taskSatisfied = false;
        liftMotor = null;
    }
}
