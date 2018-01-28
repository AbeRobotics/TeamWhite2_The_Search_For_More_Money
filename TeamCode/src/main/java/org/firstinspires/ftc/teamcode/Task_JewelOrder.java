package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Akanksha.Joshi on 25-Dec-2017.
 */

public class Task_JewelOrder extends IOPModeTaskBase {
    private double maxTime = OPModeConstants.ReadBallColor;
    private boolean taskSatisfied = false;
    private JewelDetector jewelDetector= null;
    private JewelDetectorFacade jewelDetectorFacade= null;
    private OPModeConstants opModeConstants = null;
    private HardwareMap hardwareMap;

    public Task_JewelOrder(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
        taskSatisfied = false;
    }
    @Override
    public boolean GetTaskStatus() {
        return taskSatisfied;
    }

    @Override
    public void PerformTask(Telemetry telemetry, double elapsedTime) {
        if(elapsedTime > maxTime){
            telemetry.addData("Time gone", elapsedTime);
            telemetry.update();
            taskSatisfied = true;
            return;
        }
        //Sanity Check
         if(opModeConstants.getDetectedOrder() == JewelDetector.JewelOrder.UNKNOWN) {
            JewelDetector.JewelOrder jewelOrder = jewelDetectorFacade.getJewelOrder(jewelDetector);
            opModeConstants.setDetectedOrder(jewelOrder);
            telemetry.addData("Jewel Order Task Sets Order to - ", jewelOrder.toString());
            telemetry.update();
            sleep(100);
        }
        if(opModeConstants.getDetectedOrder() != JewelDetector.JewelOrder.UNKNOWN) {
            OPModeConstants.JewelDetectionDisabled = true;
            jewelDetectorFacade.stop(jewelDetector);
            taskSatisfied = true;
        }
    }

    @Override
    public void Init() {
        jewelDetectorFacade = JewelDetectorFacade.getInstance();
        opModeConstants = OPModeConstants.getInstance();
        opModeConstants.setDetectedOrder(JewelDetector.JewelOrder.UNKNOWN);
        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        jewelDetector.areaWeight = 0.05;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA;
        jewelDetector.debugContours = false;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;
        jewelDetector.speed = JewelDetector.JewelDetectionSpeed.BALANCED;
        jewelDetector.rotateMat = false;
        jewelDetector.enable();
        opModeConstants = OPModeConstants.getInstance();
        jewelDetectorFacade = JewelDetectorFacade.getInstance();
    }

    @Override
    public void Reset() {
        taskSatisfied = false;
        OPModeConstants.JewelDetectionDisabled = true;
        jewelDetectorFacade.Reset();
        jewelDetectorFacade = null;
    }
}
