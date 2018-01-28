package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by akanksha.joshi on 13-Jan-2018.
 */

public class OPModeGyroHelper {
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    private static OPModeGyroHelper instance;
    private OPModeGyroHelper(){

    }
    public static OPModeGyroHelper getInstance()
    {
        if(instance==null)
        {
            instance = new OPModeGyroHelper();
        }
        return instance;
    }
    public void Init(Telemetry telemetry, HardwareMap hardwareMap)
    {
        //added code to calibrate sensor
        //note to self - can remove calibration safely if error happens
        //calibrateSensor(); // issue with calibration ... leave it commented
        /// end of calibration code
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        if(imu == null) {

            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled = true;
            parameters.loggingTag = "Gyro";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            //sensor type is ADA Fruit
            // name it 'Gyro' on hardware config
            imu = hardwareMap.get(BNO055IMU.class, "Gyro");
            imu.initialize(parameters);
        }
    }
    public void Reset(){
        imu = null;
    }

    public double GetGyroAngle()
    {

        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double currentAngle = angles.firstAngle;
        return currentAngle;
    }


}
