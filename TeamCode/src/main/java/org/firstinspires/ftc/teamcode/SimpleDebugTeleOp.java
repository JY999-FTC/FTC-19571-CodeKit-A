package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SimpleDebugTeleOp extends OpMode {

    final double ARM_ANGLE_TICKS_PER_DEGREE = 39.0;

    DcMotor motorTest;
    boolean useRightStick = false;

    @Override
    public void init() {
      motorTest = hardwareMap.get(DcMotor.class, "motorTest");
    }

    @Override
    public void loop() {
        double power = 0;

        if (gamepad1.y) {
            useRightStick = true;
        }

        double inputSpeed;
        if (useRightStick) {
            inputSpeed = -gamepad1.right_stick_y;
        } else {
            inputSpeed = -gamepad1.left_stick_y;
        }

        // put some "slop" in, in case joystick is too sensitive
        if (Math.abs(inputSpeed) < .1) {
            inputSpeed = 0;
        }

        if (gamepad1.a) {
            power = inputSpeed;
        }
        motorTest.setPower(power);

        telemetry.addData("power","%.1f", power);
        // "%.1f" is a percentage of a float, truncating after more than the tenths place.
        telemetry.addData("position", "%7d", motorTest.getCurrentPosition());
        // "%7d" is a percentage of an integer, truncating after more than seven digits.
        double degrees = motorTest.getCurrentPosition()/ARM_ANGLE_TICKS_PER_DEGREE;

        telemetry.addData("tickAngles", "%.1f", degrees);
    }
}
