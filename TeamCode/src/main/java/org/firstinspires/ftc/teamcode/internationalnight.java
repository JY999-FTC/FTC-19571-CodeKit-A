
package org.firstinspires.ftc.teamcode;
import android.service.autofill.DateValueSanitizer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp
public class internationalnight extends LinearOpMode {
    public DcMotor spin;
    public void runOpMode() throws InterruptedException{
        spin= hardwareMap.get(DcMotor.class,"Spin");
        spin.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            sleep(1000);
            spin.setTargetPosition(-591);
            spin.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            spin.setPower(.2);
            while (spin.isBusy()&&opModeIsActive()){

            }
            sleep(1000);
            spin.setTargetPosition(0);
            spin.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            spin.setPower(.2);
            while (spin.isBusy()&&opModeIsActive()){

            }

        }
    }

}
