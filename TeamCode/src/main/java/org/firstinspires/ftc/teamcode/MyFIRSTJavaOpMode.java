package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by 500260501 on 13/11/2017
 * Alexander Aldridge promptly attempted to improve it
 * Riley James then attempted to further improve it
 * Ibrahim Sultan was there for the heck of it!
 */
@TeleOp(name="OpMode 2.1: The Journey for more Money", group="Teleop")
//@Disabled
public class MyFIRSTJavaOpMode extends OpMode {
    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor shoulder;
    private DcMotor elbow;

    private Servo leftClaw;
    private Servo rightClaw;
    private Servo gemArm;
    //DcMotor liftMotor;

    private double leftWheelPower, rightWheelPower, shoulderPower, elbowPower, xValue, yValue;
    private boolean rightBumper, leftBumper, buttonA, buttonB, rememberRightBumper, clawOpen;

    private double leftClawClosePosition = 0.45;
    private double leftClawOpenPosition = 0.75;
    private double rightClawClosePosition = 0.55;
    private double rightClawOpenPosition = 0.20;
    private double gemArmDownPosition = 1;
    private double gemArmUpPosition = 0.51;
    private boolean skeetDown = false;
    private int shoulderPosition;
    private int aTicks = 0;     //counts number of ticks the a button has been held down to prevent the littleArmThingFeaturingSkeet from shaking
    @Override
    public void init()
    {
        leftWheel = hardwareMap.dcMotor.get("left_wheel");
        rightWheel = hardwareMap.dcMotor.get("right_wheel");
        shoulder = hardwareMap.dcMotor.get("shoulder");
        elbow = hardwareMap.dcMotor.get("elbow");

        leftClaw = hardwareMap.servo.get("left_claw");
        rightClaw = hardwareMap.servo.get("right_claw");
        gemArm = hardwareMap.servo.get("little_arm_thing");

        rightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rememberRightBumper = false;
        clawOpen = false;
        rightBumper = false;

    }

    @Override
    public void loop()
    {
        rightBumper = gamepad1.right_bumper;
        leftBumper = gamepad1.left_bumper;
        buttonA = gamepad1.a;
        buttonB = gamepad1.b;


        yValue = gamepad1.right_stick_y;
        xValue = gamepad1.left_stick_x;

        leftWheelPower =  xValue * 0.8 - yValue; //Turning Speed = 1.1
        rightWheelPower = xValue * 0.8 + yValue;

        leftWheel.setPower(Range.clip(leftWheelPower, -1.0, 1.0));
        rightWheel.setPower(Range.clip(rightWheelPower, -1.0, 1.0));

        if (rightBumper && !rememberRightBumper){
            clawOpen = !clawOpen;
        }

        if (!clawOpen){
            leftClaw.setPosition(leftClawClosePosition);
            rightClaw.setPosition(rightClawClosePosition);
        }

        if (clawOpen){
            leftClaw.setPosition(leftClawOpenPosition);
            rightClaw.setPosition(rightClawOpenPosition);
        }

        if (buttonA){
            if (aTicks < 11) {
                if (skeetDown) {
                    skeetDown = false;
                    gemArm.setPosition(gemArmUpPosition);
                }
                else {
                    skeetDown = true;
                    gemArm.setPosition(gemArmDownPosition);
                }
                aTicks++;
            }
        }
        else{
            aTicks = 0;
        }

        if (gamepad1.right_trigger > 0.01){
            shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shoulderPower = gamepad1.right_trigger * 0.75 * -1;
            shoulder.setPower(shoulderPower);
            //elbow.setPower(shoulderPower/-5);
        }
        else if (gamepad1.left_trigger > 0.01){
            shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shoulderPower = gamepad1.left_trigger * 0.75;
            shoulder.setPower(shoulderPower);
            //elbow.setPower(shoulderPower/-1);
        }
        else{
//            shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            shoulderPosition = shoulder.getCurrentPosition();
//            shoulder.setTargetPosition(shoulderPosition);
//            shoulder.setPower(-0.5);
            shoulder.setPower(0);
        }
        if (gamepad1.dpad_left){
            elbow.setPower(-0.25);
        }
        else if (gamepad1.dpad_right){
            elbow.setPower(0.25);
        }
        else{
            elbow.setPower(0);
        }

        rememberRightBumper = rightBumper;

    }
}