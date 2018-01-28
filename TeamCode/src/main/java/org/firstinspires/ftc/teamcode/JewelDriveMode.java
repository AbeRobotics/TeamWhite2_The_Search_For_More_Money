package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Created by Akanksha.Joshi on 23-Dec-2017.
 */

public class JewelDriveMode {

    private static JewelDriveMode jewelDriveInstance;
    OPModeConstants.FireSequence fireSequence;
    private JewelDriveMode()
    {}
    public static JewelDriveMode jewelDriveModeInstance()
    {
        if(jewelDriveInstance == null)
        {
            jewelDriveInstance = new JewelDriveMode();
        }
        return jewelDriveInstance;
    }
    public boolean performJewelRemovalTask(OPModeConstants.FireSequence fireSequence, HardwareMap hardwareMap, Telemetry telemetry)
    {

        OPModeDriveHelper helper = OPModeDriveHelper.getInstance();
        helper.Init(telemetry,hardwareMap);
        //FWD - AKA Right wheel negative, left wheel positive
        if(fireSequence == OPModeConstants.FireSequence.FORWARD) {
            helper.Turn(25, OPModeConstants.AutonomousSpeed.SLOW,true);


        }
        //Back - AKA Right wheel positive, left wheel negative
        if(fireSequence == OPModeConstants.FireSequence.BACKWARD) {
            helper.Turn(-25, OPModeConstants.AutonomousSpeed.SLOW,true);

        }



        return true;
    }
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void Reset()
    {
        jewelDriveInstance = null;
    }
}
