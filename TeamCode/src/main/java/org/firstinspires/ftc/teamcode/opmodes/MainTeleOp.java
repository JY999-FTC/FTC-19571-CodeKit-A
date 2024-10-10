package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class MainTeleOp extends BaseTeleOp {
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            robot.IMU_Update();
            double target = robot.anglePID.getTarget() + (-gamepad1.right_stick_x * robot.turningInputConstant * robot.getDeltaTime());
            robot.anglePID.setTarget(target);
            robot.anglePID.update(robot.getRobotAngle());
            robot.driveTrain.setDrivePower(-gamepad1.left_stick_y, gamepad1.left_stick_x, robot.anglePID.getPower(), robot.getRobotAngle());
            telemetry.addData("Robot Angle",robot.getRobotAngle());
            telemetry.addData("Target Angle",target);
            telemetry.addData("Robot Power",robot.anglePID.getPower());
            telemetry.update();
            
            }
           
        }
    }

