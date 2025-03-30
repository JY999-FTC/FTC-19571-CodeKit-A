/*
Author: Jerome Yang
Start: 3/29/2025
End: #/##/####
Purpose: FTC 19571 The Robo Brigade Team D testbed robot code.
*/

// Package and imports are outside code & information that is referenced or used
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

// @TeleOp means this will show up as teleop
@TeleOp

// public class needs to be same name as the document. extend LinearOpMode because it inherits its code
public class Jerome_Yang extends LinearOpMode {

    // Variables
    double motorPosition = 0;
    double servoPosition = 0;

    // Declarations of hardware, Best practice to declare in class because if declare in runOpMode can only be used in there
    //DcMotorEx MotorT; // This is declared with a encoder to track # of rotations. DcMotor for no encoder
    DcMotor MotorT; // this is declared without a encoder

    //Servo ServoT;// This is declared as a servo, which moves to a position, it could also be CRServo which is a Continuous Servo. The Servo can be programmed between the 2 modes
    CRServo ServoT; // This is declared as a Continues Rotation Servo which rotates around at a speed

    // There is a runOpMode method in LinearOpMode, which is the parent class of this subclass, so Override means that runOpMode in here overrides the original runOpMode
    @Override

    // This is the method where the code goes
    public void runOpMode() throws InterruptedException {

        // Declarations of hardware, make sure name & configuration matches
        //MotorT = hardwareMap.get(DcMotorEx.class, "MotorT");
        MotorT = hardwareMap.get(DcMotor.class, "MotorT"); //For no encoder motor

        //ServoT = hardwareMap.get(Servo.class, "ServoT");
        ServoT = hardwareMap.get(CRServo.class, "ServoT"); //For Continuous Servo


        // Set up motor
        MotorT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the encoder to 0
        MotorT.setPower(0); // Power is the electricity given. from -1(reverse full power) to 1 (full power)
        MotorT.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // if no electricity then use power to stop motor. Good for linear slidwe
        MotorT.setTargetPosition(0); // Target position of robo is 0
        MotorT.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Spin to the target position
        //MotorT.setVelocity(2000); // Uses Encoder to detect and adjust velocity to constant a RPM

        // Set up Servo
        //ServoT.setPosition(0); // Reset the servo, if CRServo then will spin

        // Wait for start button pressed method, if not start then rerun code
        waitForStart();

        // Determine whether the OpMode has been asked to stop by button press
        if (isStopRequested()) return;

        // While loop runs multiple times per second when opMode is active
        while (opModeIsActive()) {

            // This is a Method, they are defined and can be used multiple times to save time and space
            moveMotorWithEncoder(); // Spins Motor to a position based on gamepad1 right joystick x axis
            moveMotorNoEncoder(); // Spins Motor in a direction based on gamepad1 right joystick x axis

            //moveServo(); // Spins servo to a position based on gamepad1 left joystick x axis
            moveCRServo(); // Spins servo in a direction based on gamepad1 left joystick x axis

            updateTelemetry(); // A method to display information

        }// while loop end

    }// OpMode end

    // This is a method outside of the while loop, can be used inside the loop.
    public void moveMotorWithEncoder(){
        if (gamepad1.right_stick_x != 0) { // if statement, there is 2 gamepads and gamepad1.right_stick_x means a # between -1 to 1
            motorPosition += gamepad1.right_stick_x * 5; // motorPosition is a varible and joystick adjust it
            MotorT.setTargetPosition((int) motorPosition); // Motor has a encoder so can move to a target position, (int) to get rid of decimals
        }
    } // moveMotorWithEncoder end

    public void moveMotorNoEncoder(){
        if (gamepad1.right_stick_x != 0) {
            MotorT.setDirection(DcMotorSimple.Direction.FORWARD); // set direction of movement
            MotorT.setTargetPosition(100);
            MotorT.setPower(gamepad1.right_stick_x); // Motor power is set by joystick
        }
    } // moveMotorNoEncoder end

    // This is a method outside of the while loop, can be used inside the loop.
    public void moveServo(){
        if (gamepad1.left_stick_x != 0) {
            servoPosition += gamepad1.left_stick_x * 0.002; // servoPosition is a varible adjusted by joystick. Note Servo can only go 0 to 1 but varible can go higher
            //ServoT.setPosition(servoPosition); // position only for servo, CRServo is setDirection()
        }
    } // moveServo end

    public void moveCRServo(){
        if (gamepad1.left_stick_x != 0) {
            //ServoT.setPower(gamepad1.left_stick_x); // position only for servo, CRServo is setDirection()
        }
    } // moveCRServo end

    // Telemetry is the display on the right of the start button in the driver hub.
    public void updateTelemetry() {
        telemetry.addData("Left JoyStick: ", gamepad1.left_stick_x);
        telemetry.addLine();
        telemetry.addData("Right JoyStick: ", gamepad1.right_stick_x);
        telemetry.addLine();
        telemetry.addData("Motor Target Position(Need Encoder):", motorPosition);
        telemetry.addLine();
        telemetry.addData("Servo Target Position(Need Servo Mode): ", servoPosition);
        telemetry.addLine();
        telemetry.addData("Motor Actual Position:", MotorT.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("Servo Actual Position: ", ServoT.getPosition());
        telemetry.addLine();
        telemetry.addData("Motor Power:", MotorT.getPower());
        telemetry.addLine();
        telemetry.addData("Servo Power: ", ServoT.getPower());
        telemetry.addLine();
        telemetry.update();
    }//update telemetry end


}// class end
