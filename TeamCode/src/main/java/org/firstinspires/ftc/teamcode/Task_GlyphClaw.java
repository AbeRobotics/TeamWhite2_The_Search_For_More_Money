package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by akanksha.joshi on 31-Dec-2017.
 */

public class Task_GlyphClaw extends IOPModeTaskBase {
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;
    private OPModeConstants.GlyphClawPosition clawPosition;
    Servo leftClaw = null;
    Servo rightClaw = null;

    public Task_GlyphClaw(HardwareMap hardwareMap, OPModeConstants.GlyphClawPosition clawPosition)
    {
        this.hardwareMap = hardwareMap;
        this.clawPosition = clawPosition;
        taskSatisfied = false;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {

        if (elapsedTime > OPModeConstants.ReleaseGlyph && !OPModeConstants.DEBUG && clawPosition== OPModeConstants.GlyphClawPosition.CLOSE) {
            taskSatisfied = true;
            return;
        }
        if(clawPosition== OPModeConstants.GlyphClawPosition.CLOSE && !OPModeConstants.DEBUG && elapsedTime > OPModeConstants.PickGlyph)
        {
            taskSatisfied = true;
            return;
        }

        if(clawPosition == OPModeConstants.GlyphClawPosition.CLOSE)
        {
            leftClaw.setPosition(0.65);
            rightClaw.setPosition(0.35);
        }
        if(clawPosition == OPModeConstants.GlyphClawPosition.OPEN)
        {
            leftClaw.setPosition(0.15);
            rightClaw.setPosition(0.85);
        }
        if(clawPosition != OPModeConstants.GlyphClawPosition.UNKNOWN)
        {
            taskSatisfied = true;
        }
    }

    @Override
    public void Init() {
        opModeConstants = OPModeConstants.getInstance();
         leftClaw = hardwareMap.servo.get("left_claw");
         rightClaw = hardwareMap.servo.get("right_claw");
    }

    @Override
    public void Reset() {
        taskSatisfied = false;
    }
}
