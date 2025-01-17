package org.firstinspires.ftc.teamcode.opmode.autos;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.utils.BT.BTRecordedController;
import org.firstinspires.ftc.teamcode.utils.BT.BTRecordingController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class playback extends periodOpMode{
    BTRecordedController controller;
    BufferedReader bufferedReader;
    final int maxIterations=20*30;//Hz * Sec

    @Override
    public void initialize() {
        final String file_name="11226_rec";
        try {
            File log = AppUtil.getInstance().getSettingsFile(file_name);
            bufferedReader=new BufferedReader(new FileReader(log));
            controller=new BTRecordedController(gamepad1,bufferedReader,maxIterations);
            m_robot=new RobotContainer(hardwareMap,controller);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void period() {
        controller.next();
    }

    @Override
    protected void endFunction() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
