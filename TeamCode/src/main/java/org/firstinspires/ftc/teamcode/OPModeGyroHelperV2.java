package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;

/**
 * Created by akanksha.joshi on 13-Jan-2018.
 */

public class OPModeGyroHelperV2 {
    BNO055IMU imu;
    Orientation angles;

    HardwareMap hardwareMap;
    Telemetry telemetry;


    public double GetGyroAngle(Telemetry telemetry, HardwareMap hardwareMap)
    {
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
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double currentAngle = angles.firstAngle;
        telemetry.addData("Gyro V2 Angle", currentAngle);
        return currentAngle;
    }


}
