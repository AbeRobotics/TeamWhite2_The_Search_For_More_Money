package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by akanksha.joshi on 25-Dec-2017.
 */

public class Task_GetCryptoKey extends IOPModeTaskBase {
    private double maxTime = OPModeConstants.ReadPictograph;
    private boolean taskSatisfied = false;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap = null;
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia = null;
    VuforiaTrackable relicTemplate = null;
    VuforiaTrackables relicTrackables = null;
    Telemetry telemetry = null;
    public Task_GetCryptoKey(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        if(elapsedTime > maxTime){
            taskSatisfied = true;
            return;
        }
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            telemetry.addData("Found Relic ",vuMark);
            telemetry.update();
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                //TODO - Use this to re orient the robot
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
            opModeConstants.setCryptoLocation(vuMark);
            if(relicTrackables!=null)
            {
                relicTrackables.deactivate();
            }
            taskSatisfied = true;

        }


    }

    @Override
    public void Init() {
        opModeConstants = OPModeConstants.getInstance();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARu0pnX/////AAAAGU7XB2WmyU5kv3ajbeT3HhRf7ACI" +
                "3GFxKNWK2EwbVeTa1hXzuQbGfR7dsY1LMIs4pT04HYGuNnVhAp1tSsaqFNgkshKf98ER/MSIecMKhl4NQv" +
                "FmW6WKA7xOMLJZKYe/T7bE1K/PJm53beyC2iqr9RXb6Yo8YLnTtHdMOv/Tj3jn6hW0drXzqmTGBW54BKz+fT" +
                "0z27n9P+O/73olp4OYrRYR3tdr81en2sZaHYISZdF8ryKhPIgTIg98+C8WowtFXrB1C2b40dc78NXxWxCgEHt" +
                "5YIDEXFTSLjB7wfr8FogqiPjBRGoiPwDrY9oRTpsXKMeZmDccEnjNOukwQWx/D6dPSL5lG1jECsVGWDHnzfaO";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();
    }

    @Override
    public void Reset() {

    }
}
