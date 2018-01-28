package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 500260501 on 13/11/2017.
 * Alexander Aldridge promptly attempted to improve it;
 * Riley James then attempted to further improve it
 * Ibrahim Sultan was there for the heck of it!
 */
@TeleOp(name="OpMode 2.1: The Journey for more Money", group="Teleop")
//@Disabled
public class MyFIRSTJavaOpMode extends OpMode {
    DcMotor leftWheel;
    DcMotor rightWheel;
    DcMotor shoulder;
    DcMotor elbow;

    Servo leftClaw;
    Servo rightClaw;
    Servo gemArm;
    //DcMotor liftMotor;

    double leftWheelPower;
    double rightWheelPower;
    double shoulderPower;
    double elbowPower;
    double leftClawClosePosition = 0.49;
    double leftClawOpenPosition = 0.75;
    double rightClawClosePosition = 0.5;
    double rightClawOpenPosition = 0.25;
    double gemArmDownPosition = 1;
    double gemArmUpPosition = 0.51;
    boolean skeetDown = false;
    int shoulderPosition;
    int aTicks = 0;     //counts number of ticks the a button has been held down to prevent the littleArmThingFeaturingSkeet from shaking
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

        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        //leftClawClosePosition = leftClaw.get

        //leftClaw.setPosition(0.5);
        //rightClaw.setPosition(0.5);
        //gemArm.setPosition(gemArmUpPosition);

    }

    @Override
    public void loop()
    {
        leftWheelPower = -gamepad1.left_stick_y;
        rightWheelPower = gamepad1.right_stick_y;

        leftWheel.setPower(leftWheelPower);
        rightWheel.setPower(rightWheelPower);

        if (gamepad1.left_bumper){
            leftClaw.setPosition(leftClawClosePosition);
            rightClaw.setPosition(rightClawClosePosition);
        }

        if (gamepad1.right_bumper){
            leftClaw.setPosition(leftClawOpenPosition);
            rightClaw.setPosition(rightClawOpenPosition);
        }

        if (gamepad1.a){
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
        }
        else if (gamepad1.left_trigger > 0.01){
            shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shoulderPower = gamepad1.left_trigger * 0.75;
            shoulder.setPower(shoulderPower);
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
    }
}