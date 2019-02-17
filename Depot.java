package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.Locale;

@Autonomous(name = "Autonomous Depot",group = "LinearOpMode")
public class Depot extends LinearOpMode {

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     P_DRIVE_COEFF           = 0.15;
    static final double     HEADING_THRESHOLD       = 1 ;
    static final double     P_TURN_COEFF            = 0.1;
    private static final String VUFORIA_KEY = "AZ/y08v/////AAABmW2F+VgsfEqurugLGLrbk3xyH7N9tvzPux4eQg9rPK+SIv5GDmkrIx0vgN" +
            "TWk38gl/twDCosIHE+QKNfrRJ52UEUApnutRqNvEbblyi/uhiqOnJsEBVJnZeiI/Ix+ZZdt2i7g+juzZqYVINYv1p0mOWPDdP" +
            "L77UyWLdwdeHKYe7LJo3SbAbzrH5enUwDRalJ2MmSsXg3xm9rXJlS1RQ2RoDSIVhh101KgF33QlDFnK/8yBRqHbEMfxsb5df8" +
            "gIWnFv/wkQWwUFd1fH/w0VWLjWfX5O5HuvAZJ5fDSq2rVy+i0EbKLsXn/heEQuRJgU409sMEpOKxMYX7em673DL9qP7A3p6dp" +
            "SeoOTH5QDmcv0EJ";

    VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    HardwareSigma2019 robot = null;
    ElapsedTime runtime = new ElapsedTime();
    ElapsedTime keepPhoneAlive = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        keepPhoneAlive.reset();
        robot = new HardwareSigma2019();
        robot.init(hardwareMap);
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

        //waitForStart();

        // Loop and update the dashboard
        while (opModeIsActive() == false) {
            telemetry.addData("KEEPS THE CONNECTION TO THE PHONE", keepPhoneAlive);
            telemetry.update();
        }
        String x = ImageSensing();
        Delatch();
        //sleep(100);
        if(x.equals("Right"))
        {
            encoderDrive(1,3.1,-3.1,100);
            sleep(200);
            encoderDrive(1,15,15,100);
            sleep(200);
            encoderDrive(1,-7,-7,100);
            sleep(200);
            encoderDrive(1,-13,13,100);
            sleep(200);
            encoderDrive(1,20,20,100);
            sleep(200);
            encoderDrive(1,12.2,-12.2,100);
            sleep(200);
            encoderDrive(1,19,19,100);
            dropTheMarker();
            //sleep(200);
            encoderDrive(1,2.25,-2.25,100);
            sleep(200);
            encoderDrive(1,-17.5,-17.5,100);
            sleep(200);
            encoderDrive(1,20.25,-20.25,100);
            sleep(200);
            encoderDrive(1,11,11,100);
        }
        else if(x.equals("Left"))
        {
            encoderDrive(1,-3.3,3.3,100);
            sleep(200);
            encoderDrive(1,22,22,100);
            sleep(200);
            encoderDrive(1,7.8,-7.8,100);
            sleep(200);
            encoderDrive(1,6,6,100);
            //sleep(200);
            dropTheMarker();
            //sleep(200);
            encoderDrive(1,18.95,-18.95,100);
            sleep(200);
            encoderDrive(1,33,33,100);

        }
        else if(x.equals("Center"))
        {
            encoderDrive(1,12,12,100);
            sleep(200);
            encoderDrive(1,-4,-4,100);
            sleep(200);
            encoderDrive(1,-10.25,10.25,100);
            sleep(200);
            encoderDrive(1,16.5,16.5,100);
            sleep(200);
            encoderDrive(1,12.4,-12.4,100);
            sleep(200);
            encoderDrive(1,21,21,100);
            dropTheMarker();
            //sleep(200);
            encoderDrive(1,2.25,-2.25,100);
            sleep(200);
            encoderDrive(1,-17.5,-17.5,100);
            sleep(200);
            encoderDrive(1,20.65,-20.65,100);
            sleep(200);
            encoderDrive(1,15,15,100);
        }



                 //This block of code deals with hanging
        //sleep(10000);
        SetBackToNormal();
        //Latch();
        //SetBackToNormal2();


    }
    public void dropTheMarker()
    {
        robot.Intake.setPower(-1);
        sleep(4000);
        robot.Intake.setPower(0);
    }
    public void hangMoveWithExtension(double speed,
                                      double ticks,double ticks2,
                                      double timeoutS) {
        int newTarget;
        int newTarget2;
        boolean isDone = false;


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.HangingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.SpoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            newTarget = robot.HangingMotor.getCurrentPosition() + (int)ticks;
            robot.HangingMotor.setTargetPosition(newTarget);
            newTarget2 = robot.SpoolMotor.getCurrentPosition() + (int)ticks2;
            robot.HangingMotor.setTargetPosition(newTarget2);



            // Turn On RUN_TO_POSITION
            robot.HangingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.HangingMotor.setPower(Math.abs(speed));
            robot.SpoolMotor.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.HangingMotor.isBusy()) || robot.SpoolMotor.isBusy()) {

                if(robot.SpoolMotor.isBusy() == false && isDone == false)
                {
                    robot.SpoolMotor.setPower(0);
                    isDone = true;
                }
                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d",
                        robot.HangingMotor.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.HangingMotor.setPower(0);
            robot.SpoolMotor.setPower(0);



            // Turn off RUN_TO_POSITION
            robot.HangingMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void armTime(double time)
    {
            robot.Right.setPower(-.5);
            robot.Left.setPower(-.5);
            long time2 = (long)time * 1000;
            sleep(time2);
            robot.Right.setPower(0);
            robot.Left.setPower(0);
    }
    public String ImageSensing() {

        String blockLocation ="";
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        telemetry.update();
                        if (updatedRecognitions.size() >= 2 && updatedRecognitions != null) {
                            //Set default values for all mineral x and y values
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            int goldMineralY = -1;
                            int silverMineral1Y = -1;
                            int silverMineral2Y = -1;
                            int mineral1X = -1;
                            int mineral2X = -1;
                            int mineral1Y = -1;
                            int mineral2Y = -1;
                            int mineral1Type = -1; //0 - silver; 1 - gold
                            int mineral2Type = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                //Get the detected 2 minerals with the largest Y values

                                if (recognition.getLabel().equals(LABEL_SILVER_MINERAL) || recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    telemetry.addData("status", "detected gold or silver");
                                    telemetry.update();
                                    if (recognition.getTop() > mineral1Y && updatedRecognitions.size() >= 3) {
                                        //If the current mineral has a Y value greater than the previous highest or second highest, set it to the correct ranking
                                        //If it is the highest, push the old highest to second highest, and make this on the highest
                                        //If it is second highest, just replace the value
                                        mineral2X = mineral1X;
                                        mineral2Y = mineral1Y;
                                        mineral2Type = mineral1Type;
                                        mineral1X = (int) recognition.getLeft();
                                        mineral1Y = (int) recognition.getTop();
                                        if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                                            silverMineral1X = mineral1X;
                                            silverMineral1Y = mineral1Y;
                                            mineral1Type = 0;
                                        } else {
                                            //Same for the gold minerals
                                            goldMineralX = mineral1X;
                                            goldMineralY = mineral1Y;
                                            mineral1Type = 1;
                                        }
                                    } else if (recognition.getTop() > mineral2Y && updatedRecognitions.size() >= 3) {
                                        mineral2X = (int) recognition.getLeft();
                                        mineral2Y = (int) recognition.getTop();
                                        if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                                            silverMineral2X = mineral2X;
                                            silverMineral2Y = mineral2Y;
                                            mineral2Type = 0;
                                        } else {
                                            goldMineralX = mineral2X;
                                            goldMineralY = mineral2Y;
                                            mineral2Type = 1;
                                        }
                                    } else if (updatedRecognitions.size() == 2) {
                                        //if it only detects 2 minerals
                                        telemetry.addData("Status", "Two minerals detected");
                                        telemetry.update();
                                        if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                                            if (silverMineral1X == -1) {
                                                silverMineral1X = (int) recognition.getLeft();
                                                silverMineral1Y = (int) recognition.getTop();
                                                mineral1Type = 0;
                                            } else if (silverMineral2X == -1) {
                                                silverMineral2X = (int) recognition.getLeft();
                                                silverMineral2Y = (int) recognition.getTop();
                                                mineral2Type = 0;
                                            }
                                        } else {
                                            if (goldMineralX == -1) {
                                                goldMineralX = (int) recognition.getLeft();
                                                goldMineralY = (int) recognition.getTop();
                                                if (mineral1Type == -1)
                                                    mineral1Type = 1;
                                                else
                                                    mineral2Type = 1;
                                            }
                                        }

                                    }
                                }
                            }
                            telemetry.addData("Mineral 1 type" + mineral1Type, "Mineral 2 type" + mineral2Type);
                            telemetry.update();
                            //Find the location of the gold mineral based on the relative location to the silver minerals
                            if (mineral1Type == 0 && mineral2Type == 0) {
                                //gold mineral not detected among the right 2 positions, so it is at left
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                                blockLocation = "Left";
                                if (tfod != null) {
                                    tfod.shutdown();
                                }
                                break;
                            } else if (mineral1Type == 0 && mineral2Type == 1) {
                                if (goldMineralX < silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    telemetry.update();
                                    blockLocation = "Center";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                    return blockLocation;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    telemetry.update();
                                    blockLocation = "Right";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                    return blockLocation;

                                }
                            }
                            else if (mineral1Type == 1 && mineral2Type == 0) {
                                if (goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    telemetry.update();
                                    blockLocation = "Center";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                    break;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    telemetry.update();
                                    blockLocation = "Right";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                    return blockLocation;
                                }
                            }
                        }
                        telemetry.update();
                    }
                        }
                    }
                }
        if (tfod != null) {
            tfod.shutdown();
        }
        return blockLocation;
    }
    public void initCam() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            newLeftTarget = robot.FrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.FrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.FrontLeft.setTargetPosition(newLeftTarget);
            robot.FrontRight.setTargetPosition(newRightTarget);
            robot.BackLeft.setTargetPosition(newLeftTarget);
            robot.BackRight.setTargetPosition(newRightTarget);


            // Turn On RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FrontLeft.setPower(Math.abs(speed));
            robot.FrontRight.setPower(Math.abs(speed));
            robot.BackLeft.setPower(Math.abs(speed));
            robot.BackRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.FrontLeft.isBusy() && robot.FrontRight.isBusy() && robot.BackLeft.isBusy() && robot.BackRight.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                        robot.FrontLeft.getCurrentPosition(),
                        robot.FrontRight.getCurrentPosition(),
                        robot.BackLeft.getCurrentPosition(),
                        robot.BackRight.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.FrontLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackLeft.setPower(0);
            robot.BackRight.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void strafeLeftDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            newLeftTarget = robot.FrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.FrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);



            robot.FrontLeft.setTargetPosition(-newLeftTarget);
            robot.FrontRight.setTargetPosition(newRightTarget);
            robot.BackLeft.setTargetPosition(newLeftTarget);
            robot.BackRight.setTargetPosition(-newRightTarget);


            // Turn On RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FrontLeft.setPower(Math.abs(speed));
            robot.FrontRight.setPower(Math.abs(speed));
            robot.BackLeft.setPower(Math.abs(speed));
            robot.BackRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.FrontLeft.isBusy() && robot.FrontRight.isBusy() && robot.BackLeft.isBusy() && robot.BackRight.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                        robot.FrontLeft.getCurrentPosition(),
                        robot.FrontRight.getCurrentPosition(),
                        robot.BackLeft.getCurrentPosition(),
                        robot.BackRight.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.FrontLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackLeft.setPower(0);
            robot.BackRight.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void strafeRightDrive(double speed,
                                double leftInches, double rightInches,
                                double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.FrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.FrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);



            robot.FrontLeft.setTargetPosition(newLeftTarget);
            robot.FrontRight.setTargetPosition(-newRightTarget);
            robot.BackLeft.setTargetPosition(-newLeftTarget);
            robot.BackRight.setTargetPosition(newRightTarget);


            // Turn On RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FrontLeft.setPower(Math.abs(speed));
            robot.FrontRight.setPower(Math.abs(speed));
            robot.BackLeft.setPower(Math.abs(speed));
            robot.BackRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.FrontLeft.isBusy() && robot.FrontRight.isBusy() && robot.BackLeft.isBusy() && robot.BackRight.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                        robot.FrontLeft.getCurrentPosition(),
                        robot.FrontRight.getCurrentPosition(),
                        robot.BackLeft.getCurrentPosition(),
                        robot.BackRight.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.FrontLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackLeft.setPower(0);
            robot.BackRight.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void gyroDrive ( double speed,
                            double distance,
                            double angle) {

        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH);

            robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            newLeftTarget = robot.FrontLeft.getCurrentPosition() + moveCounts;
            newRightTarget = robot.FrontRight.getCurrentPosition() + moveCounts;


            // Set Target and Turn On RUN_TO_POSITION
            robot.FrontLeft.setTargetPosition(newLeftTarget);
            robot.FrontRight.setTargetPosition(newRightTarget);
            robot.BackLeft.setTargetPosition(newLeftTarget);
            robot.BackRight.setTargetPosition(newRightTarget);

            robot.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.FrontRight.setPower(speed);
            robot.FrontLeft.setPower(speed);
            robot.BackRight.setPower(speed);
            robot.BackLeft.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (robot.FrontLeft.isBusy() && robot.FrontRight.isBusy() && robot.BackLeft.isBusy() && robot.BackRight.isBusy())) {

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.FrontLeft.setPower(leftSpeed);
                robot.FrontRight.setPower(rightSpeed);
                robot.BackLeft.setPower(leftSpeed);
                robot.BackRight.setPower(rightSpeed);

                // Display drive status for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);
                telemetry.addData("Actual",  "%7d:%7d:%7d:%7d",      robot.FrontLeft.getCurrentPosition(),
                        robot.FrontRight.getCurrentPosition(),robot.BackRight.getCurrentPosition(),robot.BackLeft.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            // Stop all motion;
            robot.FrontLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackLeft.setPower(0);
            robot.BackRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
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
                        return formatAngle(robot.angles.angleUnit, robot.angles.firstAngle);
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
    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        robot.FrontRight.setPower(leftSpeed);
        robot.FrontLeft.setPower(rightSpeed);
        robot.BackRight.setPower(leftSpeed);
        robot.BackLeft.setPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.angles.firstAngle;
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }
    public void gyroTurn (  double speed, double angle) {

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
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
                telemetry.addData("Path2",  "Running at %7d",
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
                telemetry.addData("Path2",  "Running at %7d",
                        robot.SpoolMotor.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.SpoolMotor.setPower(0);



            // Turn off RUN_TO_POSITION
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void intakeMoveWithStall(double speed,
                           double ticks,
                           double timeoutS,double z) {
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
            robot.Left.setPower(z);
            robot.Left.setPower(z);


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.SpoolMotor.isBusy()) || robot.SpoolMotor.isBusy()) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d",
                        robot.SpoolMotor.getCurrentPosition());

                //telemetry.addData("Encoder Ticks for the back left motor",robot.BackLeft.getCurrentPosition());
                //telemetry.addData("Encoder Ticks for the back right motor",robot.BackRight.getCurrentPosition());

                telemetry.update();
            }

            // Stop all motion;
            robot.SpoolMotor.setPower(0);
            robot.Left.setPower(0);
            robot.Right.setPower(0);



            // Turn off RUN_TO_POSITION
            robot.SpoolMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void armMove(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            robot.Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            newLeftTarget = robot.Left.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.Right.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.Left.setTargetPosition(newLeftTarget);
            robot.Right.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Left.setPower(Math.abs(speed));
            robot.Right.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.Left.isBusy() && robot.Right.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Left.getCurrentPosition(),
                        robot.Right.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.Left.setPower(0);
            robot.Right.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void Delatch()
    {
        hangMove(1,-22330,20);
        //hangMove(1,-2000,20);
    }
    public void SetBackToNormal()
    {
        //intakeMove(1,-925,100);
        hangMove(1,22445,20);
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
}
