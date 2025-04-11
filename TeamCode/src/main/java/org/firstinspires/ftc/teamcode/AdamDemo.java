

package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Adam Demo", group="Demo")

public class AdamDemo extends LinearOpMode {


    DcMotorEx MotorA;
    Servo ServoA;
    DistanceSensor DisA;
    ColorSensor ColorA;

    DigitalChannel TouchA;

    IMU ImuA;
    double Motorpower=0.5, Servoposition=0.1;
    int runtopositon1=500;


    @Override
    public void runOpMode() {

        Hwinit();
        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {


          if(gamepad1.right_stick_y > 0.2 || gamepad1.right_stick_y < -0.2){
              Powrmode();
              MotorA.setPower(gamepad1.right_stick_y);
              sleep(500);
              MotorA.setPower(0);
          }

            if(gamepad1.square){

                RuntopositionMode();
                MotorA.setTargetPosition(runtopositon1);


            }

            if(gamepad1.circle){

                RuntopositionMode();
                MotorA.setTargetPosition(-runtopositon1);

            }

            if(gamepad1.dpad_up)
            {
                Servoposition+=0.1;
              ServoA.setPosition(Servoposition);
            }

            if(gamepad1.dpad_down)
            {
                Servoposition-=0.1;
                ServoA.setPosition(Servoposition);
            }



            telemetry.addData("Colors red", ColorA.red());
            telemetry.addData("Colors blue", ColorA.blue());
            telemetry.addData("Colors green", ColorA.green());
            telemetry.addData("Distance", DisA.getDistance(DistanceUnit.MM));
            telemetry.addData("Motor Encoder", MotorA.getCurrentPosition());
            telemetry.addData("Imu angle", ImuA.getRobotYawPitchRollAngles().getYaw((AngleUnit.DEGREES)));
            telemetry.addData("Touch Sensor", !TouchA.getState());
            telemetry.update();

        }



    }


    void RuntopositionMode()

    {

        MotorT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorT.setTargetPosition(0);
        MotorT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorT.setVelocity(500);// or set power

    }

    void Powrmode()

    {

        MotorT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sleep(100);



    }




    void Hwinit() {
        MotorT = hardwareMap.get(DcMotorEx.class, "MotorT");
        ServoT = hardwareMap.get(Servo.class, "ServoT");
        DisT = hardwareMap.get(DistanceSensor.class, "DisT");
        ColorT = hardwareMap.get(ColorSensor.class, "ColorT");
        ImuT    = hardwareMap.get(IMU.class,"ImuT");
        TouchT = hardwareMap.get(DigitalChannel.class,"TouchT");

        MotorT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MotorT.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        IMU.Parameters imuparameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        ImuT.initialize(imuparameters);
        ServoT.setPosition(0);

    }


}