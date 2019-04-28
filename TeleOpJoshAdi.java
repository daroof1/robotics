package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;

@TeleOp(name = "Tele-OpJoshAdi",group = "LinearOpMode")
public class TeleOpJoshAdi extends LinearOpMode {
    String x = "";
    HardwareSigma2019 robot = null;
    double smooth = 1;
    ElapsedTime runtime = new ElapsedTime();
    double endTimeLeftClaw = 0;
    double endTimeRightClaw = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new HardwareSigma2019();
        robot.init(hardwareMap);
        //composeTelemetry();
        //robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 100);

        boolean isExtended = false;
        boolean shouldExtend = false;
        boolean vexMotor = true;
        boolean AdiJosh = false;
        double inc = 1;
        waitForStart();
        robot.startTime();
        while(opModeIsActive() == false)
        {
            telemetry.addData("Waiting for tele-op",true);
            telemetry.update();
        }

        while(opModeIsActive())
        {
            //robot.Intake.setPower(1);
            if(gamepad2.dpad_down)
            {
                robot.HangingMotor.setPower(1);
            }
            if(gamepad2.dpad_up)
            {
                robot.HangingMotor.setPower(-1);
            }
            if(gamepad2.dpad_left)
            {
                robot.HangingMotor.setPower(0);
            }
            if(gamepad2.a &&  AdiJosh == false)
            {
                robot.Sorter.setPosition(.68);
            }
            if(gamepad2.x && AdiJosh == false)
            {
                robot.Sorter.setPosition(1);
            }
            if(gamepad2.y)
            {
                robot.Intake.setPower(0);
            }
            if(gamepad2.right_bumper && vexMotor == true)
            {
                robot.Intake.setPower(.8);
                telemetry.addData("ITS WORKING",2);
                telemetry.update();
            }
            if(gamepad2.left_bumper && vexMotor == true)
            {
                robot.Intake.setPower(-.8);
                telemetry.addData("ITS WORKING",1);
                telemetry.update();
            }
            if(gamepad2.right_bumper && vexMotor == false)
            {
                robot.Intake.setPower(1);
                telemetry.addData("ITS WORKING",1);
                telemetry.update();
            }
            if(gamepad2.left_bumper && vexMotor == false)
            {
                robot.Intake.setPower(-1);
                telemetry.addData("ITS WORKING",1);
                telemetry.update();
            }
            if(gamepad1.left_bumper)
            {
                inc = .5;
            }
            if(gamepad1.right_bumper)
            {
                inc = 1;
            }
            double speed = scale(inc * -gamepad1.right_stick_y);
            double turn = scale(inc * gamepad1.right_stick_x);
            double strafe = scale(inc * -gamepad1.left_stick_x);

            double fl = (speed + turn - strafe);
            double fr = (speed - turn + strafe);
            double bl = (speed + turn + strafe);
            double br = (speed - turn - strafe);

            robot.FrontLeft.setPower(Range.clip(fl, -1, 1));
            robot.FrontRight.setPower(Range.clip(fr, -1, 1));
            robot.BackLeft.setPower(Range.clip(bl, -1, 1));
            robot.BackRight.setPower(Range.clip(br, -1, 1));
            robot.Right.setPower(-.7 * gamepad2.right_stick_y);
            robot.Left.setPower(-.7 * gamepad2.right_stick_y);
            robot.SpoolMotor.setPower(gamepad2.left_stick_y);

            telemetry.addData("Keep connection to the phone",runtime.time());
            telemetry.addData("encoder ticks for the spool motor is", robot.SpoolMotor.getCurrentPosition());
            telemetry.update();
        }
        
    }
    public double scale(double input) {
        double scaledInput = Math.pow(input, smooth);
        if (smooth % 2 == 0) {
            if (input >= 0) {
                return scaledInput;
            } else {
                return -scaledInput;
            }
        }
        return scaledInput;
    }
    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            robot.angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            robot.gravity  = robot.imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return robot.imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return robot.imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        x = formatAngle(robot.angles.angleUnit, robot.angles.firstAngle);
                        return x;
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(robot.angles.angleUnit, robot.angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(robot.angles.angleUnit, robot.angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return robot.gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(robot.gravity.xAccel*robot.gravity.xAccel
                                        + robot.gravity.yAccel*robot.gravity.yAccel
                                        + robot.gravity.zAccel*robot.gravity.zAccel));
                    }
                });
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
    public void Delatch(Double z)
    {
        hangMove(z,-22350,20);
        //hangMove(1,-2000,20);
    }
    public void SetBackToNormal()
    {
        hangMove(1,22325,20);
    }
    public void Latch()
    {
        hangMove(1,-20375,20);
        //hangMove(.5,20375,20);
    }
    public void SetBackToNormal2()
    {
        hangMove(1,20825,20);
    }
    public void hangMove(double speed,
                             double ticks,
                             double timeoutS) {
        int newTarget;


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.HangingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            newTarget = robot.HangingMotor.getCurrentPosition() + (int)ticks;
            robot.HangingMotor.setTargetPosition(newTarget);



            // Turn On RUN_TO_POSITION
            robot.HangingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.HangingMotor.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.HangingMotor.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at :%7d",
                        robot.HangingMotor.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.HangingMotor.setPower(0);



            // Turn off RUN_TO_POSITION
            robot.HangingMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void intakeMove(double speed,
                           double ticks,
                           double timeoutS) {
        int newTarget;


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.SpoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            newTarget = robot.SpoolMotor.getCurrentPosition() + (int)ticks;
            robot.SpoolMotor.setTargetPosition(newTarget);



            // Turn On RUN_TO_POSITION
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.SpoolMotor.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.SpoolMotor.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                //telemetry.addData("Path2",  "Running at %7d",
                        //robot.SpoolMotor.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                //telemetry.update();
            }

            // Stop all motion;
            robot.SpoolMotor.setPower(0);



            // Turn off RUN_TO_POSITION
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

}
