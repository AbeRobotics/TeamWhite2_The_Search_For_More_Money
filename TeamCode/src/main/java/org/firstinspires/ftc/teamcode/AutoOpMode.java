package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Calendar;

import java.util.Timer;

/**
 * Created by Riley James on 01/24/18
 * (Actually I just copied the TeleOp mode and changed some things.)
 */
@Autonomous(name="OpMode 1.0: The Epic Quest for Better Loot Without Using Loot Boxes", group="Autonomous")
//@Disabled
public class AutoOpMode extends OpMode{
    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor shoulder;
    private DcMotor elbow;

    private Servo leftClaw;
    private Servo rightClaw;
    private Servo littleArmThingFeaturingSkeet;
    //DcMotor liftMotor;

    private double leftWheelPower;
    private double rightWheelPower;
    private double shoulderPower;
    private double elbowPower;
    private double leftClawClosePosition = 0.45;
    private double leftClawOpenPosition = 0.75;
    private double rightClawClosePosition = 0.5;
    private double rightClawOpenPosition = 0.25;
    private double littleArmFeaturingSkeetDownPosition = 1;
    private double littleArmFeaturingSkeetUpPosition = 0.55;
    private boolean skeetDown = false;
    private double  DISTANCE_TO_BALL_SET = 0;

    @Override
    public void init()
    {
        leftWheel = hardwareMap.dcMotor.get("left_wheel");
        rightWheel = hardwareMap.dcMotor.get("right_wheel");
        shoulder = hardwareMap.dcMotor.get("shoulder");
        elbow = hardwareMap.dcMotor.get("elbow");

        leftClaw = hardwareMap.servo.get("left_claw");
        rightClaw = hardwareMap.servo.get("right_claw");
        littleArmThingFeaturingSkeet = hardwareMap.servo.get("little_arm_thing");



        //leftClawClosePosition = leftClaw.get

        leftClaw.setPosition(0.5);
        rightClaw.setPosition(0.5);
        littleArmThingFeaturingSkeet.setPosition(littleArmFeaturingSkeetUpPosition);

    }

    @Override
    public void loop()
    {
        turn("right", 90);
        rightWheel.setPower(1);
        leftWheel.setPower(-1);
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            //doNothing
        }
    }
    private void turn(String direction, double angle)
    {
        Calendar cal = Calendar.getInstance();

        if (direction.toLowerCase().equals("left"))
        {
            rightWheel.setPower(1);
            leftWheel.setPower(1);
        }
        else if (direction.toLowerCase().equals("right"))
        {
            rightWheel.setPower(-1);
            leftWheel.setPower(-1);
        }

        long startTime = cal.getTimeInMillis();
        long currentTime = startTime;
        long turnTime = Math.round((628 * (angle/90.0)));

        while (currentTime < startTime + turnTime) {
            cal = Calendar.getInstance();
            currentTime = cal.getTimeInMillis();
            //try {
            //    Thread.sleep(10);
            //}
            //catch (Exception e) {
                //do nothing
            //}
        }

        rightWheel.setPower(0);
        leftWheel.setPower(0);


    }
}