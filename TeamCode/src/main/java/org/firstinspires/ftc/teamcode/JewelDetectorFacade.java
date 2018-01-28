package org.firstinspires.ftc.teamcode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.io.IOException;


public class JewelDetectorFacade
{
    private static JewelDetectorFacade jewelDetectorFacadeInstance;
    public static JewelDetectorFacade getInstance()
    {
        if(jewelDetectorFacadeInstance == null)
        {
            jewelDetectorFacadeInstance = new JewelDetectorFacade();
        }
        return jewelDetectorFacadeInstance;
    }
    private JewelDetectorFacade()    {
    }

    public JewelDetector.JewelOrder getJewelOrder (JewelDetector jewelDetector)
    {
        return jewelDetector.getCurrentOrder();
    }

    public void stop(JewelDetector jewelDetector) {
        jewelDetector.disable();
    }
    public void Reset()
    {
        jewelDetectorFacadeInstance = null;
    }

}
