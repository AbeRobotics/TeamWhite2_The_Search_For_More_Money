package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Calendar;

/**
 * Created by Alexander Aldridge on 2018-01-27
 * (Actually I just copied Riley's and changed some things.)
 */
@Autonomous(name="Auto 1.2: Park da Blue Car Mom!", group="Autonomous")
//@Disabled
public class AutoParkBlue extends OpMode{
    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor shoulder;
    private DcMotor elbow;

    private BNO055IMU emu;
    private Orientation angle;
    private AngleUnit idkwti;

    private Servo leftClaw;
    private Servo rightClaw;
    private Servo gemArm;

    private Calendar cal;

    private long time, gameStartTime, autoStartTime;
    private double leftClawClosePosition = 0.45;
    private double leftClawOpenPosition = 0.75;
    private double rightClawClosePosition = 0.5;
    private double rightClawOpenPosition = 0.25;
    private double gemArmDownPosition = 1;
    private double gemArmUpPosition = 0.55;
    private boolean gemArmDown = false;
    private double  DISTANCE_TO_BALL_SET = 0;
    //private int camera = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

    //VuforiaLocalizer.Parameters vuParams = new VuforiaLocalizer.Parameters(camera);

    @Override
    public void init()
    {
        leftWheel = hardwareMap.dcMotor.get("left_wheel");
        rightWheel = hardwareMap.dcMotor.get("right_wheel");
        shoulder = hardwareMap.dcMotor.get("shoulder");
        elbow = hardwareMap.dcMotor.get("elbow");

        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftClaw = hardwareMap.servo.get("left_claw");
        rightClaw = hardwareMap.servo.get("right_claw");
        gemArm = hardwareMap.servo.get("little_arm_thing");

        emu = hardwareMap.get(BNO055IMU.class, "emu");

        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Task_JewelOrder jewelOrder = new Task_JewelOrder(hardwareMap);
    }

    @Override
    public void loop()
    {
        cal = Calendar.getInstance();
        if (autoStartTime == 0){
            autoStartTime = cal.getTimeInMillis();
            leftClaw.setPosition(0.5);
            rightClaw.setPosition(0.5);
            gemArm.setPosition(gemArmUpPosition);
        }
        time = (cal.getTimeInMillis() - autoStartTime) / 1000;

        if (time <= 1.5){
            move("forward", 5);
        } else if (time <= 2.5){
            turn("left", 90);
        } else if (time <= 4) {
            move("forward", 5);
        }


        try{
            Thread.sleep(1000);
        }
        catch (Exception e){
            //Nada
        }
    }
    private void turn(String direction, double angle) // right or left
    {
        leftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (direction.toLowerCase().equals("left"))
        {
            rightWheel.setPower(-1);
            leftWheel.setPower(-1);
        }
        else if (direction.toLowerCase().equals("right"))
        {
            rightWheel.setPower(1);
            leftWheel.setPower(1);
        }

        long startTime = cal.getTimeInMillis();
        long currentTime = startTime;
        long turnTime = Math.round((650 * (angle/90.0)));

        while (currentTime < startTime + turnTime) {
            cal = Calendar.getInstance();
            currentTime = cal.getTimeInMillis();
        }
        rightWheel.setPower(0);
        leftWheel.setPower(0);
        leftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    private void move(String direction, int distance) // forward or backward, distance in cm
    {
        cal = Calendar.getInstance();
        if (direction.toLowerCase().equals("forward"))
        {
            rightWheel.setPower(-1);
            leftWheel.setPower(1);
        }
        else if (direction.toLowerCase().equals("backward"))
        {
            rightWheel.setPower(1);
            leftWheel.setPower(-1);
        }
        else
        {
            rightWheel.setPower(0);
            leftWheel.setPower(0);
        }
        long startTime = cal.getTimeInMillis();
        long currentTime = startTime;
        long moveTime = distance * 60;

        while (currentTime < startTime + moveTime) {
            cal = Calendar.getInstance();
            currentTime = cal.getTimeInMillis();
        }
        rightWheel.setPower(0);
        leftWheel.setPower(0);
    }
    private void claw(String position) //open or close
    {
        if (position.toLowerCase().equals("open")) // open of close
        {
            leftClaw.setPosition(leftClawOpenPosition);
            rightClaw.setPosition(rightClawOpenPosition);
        }
        else if (position.toLowerCase().equals("close"))
        {
            leftClaw.setPosition(leftClawClosePosition);
            rightClaw.setPosition(rightClawClosePosition);
        }
    }
    private void deployLittleArmThingFeaturingSkeet(String ballToRemovePosition) //right or left
    {
        if(ballToRemovePosition.toLowerCase().equals("right"))
        {
            gemArm.setPosition(gemArmDownPosition);
            move("forward", 10);
            gemArm.setPosition(gemArmUpPosition);
            move("backward", 10);
        }
        else if (ballToRemovePosition.toLowerCase().equals("left"))
        {
            gemArm.setPosition(gemArmDownPosition);
            move("backward", 10);
            gemArm.setPosition(gemArmUpPosition);
            move("forward", 10);
        }
    }
    private void moveForearm(String position) //up or down
            // This is for moving the forearm of the robot in order to put glyphs in the bottom row
            // of the cryptobox
    {
        if (position.toLowerCase().equals("down")){
            elbow.setTargetPosition(380);
            elbow.setPower(0.25);
        }
        else if(position.toLowerCase().equals("up")){
            elbow.setTargetPosition(0);
            elbow.setPower(0.25);
        }
        else{
            elbow.setPower(0);
        }
        try{
            Thread.sleep(500);
        }
        catch(Exception e){
            //doNothing
        }
    }
    private void deployMainArm()
    {
        claw("open");
        moveForearm("down");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            //Do naught a thing
        }
        claw("close");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            //Ditto
        }
        moveForearm("up");
    }
}
