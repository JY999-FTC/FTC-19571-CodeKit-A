/**
 * Code is based on https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
 * Mechanum drive TeleOp
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Control Mapping
 *  GAMEPAD1 - Drive & Manual Lift
 *      Left Joystick X - Strafe
 *      Left Joystick Y - Forward Backward
 *      Right Joystick X - Pivot
 *      Right Trigger - Lift Lower
 *      Left Trigger - Lift Raise
 *
 *  GAMEPAD2 - Arm, Arm Pivot, Intake, & Lift Sequencing
 *      Right Trigger - Intake
 *      Left Trigger - Outtake
 *      Right Bumper - Extend Arm
 *      Left Bumper - Retract Arm
 *      X Button - Raise Arm
 *      Y Button - Lower Arm
 *      A Button - Low Lift
 *      B Button - High Lift
 *
 *
 *  NOTE: Low lift must be completed before high lift is engaged. This is due to rules and the sequencing of getting to
 *      the high bar relies on the bot being already hanging from the low bar.
 */

@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends LinearOpMode {

        private static final double MAX_EXTEND = -3595;
        private static final double MAX_PIVOT = 2560;
        private static final double MIN_PIVOT = -670;
        private static final double MIN_EXTEND = 0;

        //TODO: Change Max and Min values for new lift rig
        private static final double LEFT_LIFT_MAX = -6160;
        private static final double LEFT_LIFT_MIN = 22;
        private static final double RIGHT_LIFT_MAX = -6270;
        private static final double RIGHT_LIFT_MIN = -62;

    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot(this);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double pivot = -gamepad1.right_stick_x;

            double rightLiftTrigger = gamepad1.right_trigger; //rightTrigger is raising the lift
            double leftLiftTrigger = gamepad1.left_trigger; //leftTrigger is lowering the lift


            //when x button is pressed, rotates one way
            if (gamepad2.right_trigger > 0.01) {
                bot.setIntakePosition(-1.0);
            }
            //when y button is pressed, rotates the opposite way
            else if (gamepad2.left_trigger > 0.01) {
                bot.setIntakePosition(1.0);
            }
            //when no button is pressed, nothing rotates
            else {
                bot.setIntakePosition(0.0);
            }

            //mechanum drive equations for powering each motor
            double frontLeftPower = y + x + pivot;
            double backLeftPower =  y - x + pivot;
            double frontRightPower = y - x - pivot;
            double backRightPower = y + x - pivot;

            if(rightLiftTrigger > 0.1 &&
                    (bot.getLeftLiftPos() < LEFT_LIFT_MIN)&&(bot.getRightLiftPos() < RIGHT_LIFT_MIN)){
                bot.setLift(1.0);//this makes it go down
            }
            else if(leftLiftTrigger > 0.1 &&
                    (bot.getLeftLiftPos() > LEFT_LIFT_MAX)&&(bot.getRightLiftPos() > RIGHT_LIFT_MAX)){
                bot.setLift(-1.0);//this makes it go up
            }
            else
            {
                bot.setLift(0.0);
            }
            //normalize power value
            double max = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                    Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

            if (max > 1.0){
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }
            bot.setDriveTrain(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

            //extend arm controls
            if(gamepad2.right_bumper && bot.getExtendPos() >= MAX_EXTEND){
                bot.setExtendPower(-1.0);
            } else if (gamepad2.left_bumper && bot.getExtendPos() <= MIN_EXTEND){
                bot.setExtendPower(1.0);
            } else {
                bot.setExtendPower(0.0);
            }

            if(gamepad2.a && bot.getArmPosition() <= MAX_PIVOT){
                bot.setPivotPower(0.75);
            } else if(gamepad2.b && bot.getArmPosition() >= MIN_PIVOT){
                bot.setPivotPower(-0.75);
            } else {
                bot.setPivotPower(0.0);
            }

            //Telemetry Data
            telemetry.addData("Current Extend Pos: ", bot.getExtendPos());
            telemetry.addData("Current Pivot Pos: ", bot.getPivotArmPos());
            telemetry.addData("Current Left Lift Pos: ", bot.getLeftLiftPos());
            telemetry.addData("Current Right Lift Pos: ", bot.getRightLiftPos());
            telemetry.addData("Left Front Power: ", frontLeftPower);
            telemetry.addData("Left Back Power: ", backLeftPower);
            telemetry.addData("Right Front Power", frontRightPower);
            telemetry.addData("Right Back POwer", backRightPower);
            telemetry.update();
        }
    }
}