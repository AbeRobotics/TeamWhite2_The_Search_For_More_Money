package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Akanksha.Joshi on 25-Dec-2017.
 */

public abstract class IOPModeTaskBase {
    public abstract boolean GetTaskStatus();
    public abstract void PerformTask(Telemetry telemetry, double elapsedTime);
    public abstract void Init();
    public abstract void Reset();
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void log (String key, String value)
    {
        //todo - Implement call to telemetery so our implementers can easily log whats going on
    }
}
