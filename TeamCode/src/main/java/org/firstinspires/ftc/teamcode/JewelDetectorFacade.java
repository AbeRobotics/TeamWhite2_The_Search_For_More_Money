package org.firstinspires.ftc.teamcode;

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
