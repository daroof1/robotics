package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
@Autonomous(name = "BlueDepot",group = "LinearOpMode")
public class BlueDepot extends LinearOpMode {
    // Declare OpMode Members
    HardwareSigma2018 robot = null;
    //VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        boolean Gold;
        robot = new HardwareSigma2018();
        robot.init(hardwareMap);
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        robot.relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        telemetry.addData("Status","Initialized");
        //int x = 250;
        while(opModeIsActive())
        {
            /*robot.FrontRight.setPower(1);
            robot.FrontLeft.setPower(1);
            robot.BackRight.setPower(1);
            robot.BackLeft.setPower(1);
*/
            //moveToPos(.8,-.8,24,24);
            //pause();
            //MoveMineral(1);
            //robot.
            //sleep(3000);
            if(goldDetected == false)
            {
                for(int i = 0; i < 3;i++)
                {
                    if(countGold > 1)
                    {
                        telemetry.addData("CHECK","1");
                        telemetry.update();
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(1);
                        sleep(3500-milsdone);
                        robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                        robot.Mineral.setPower(1);
                        sleep(2800);
                        goldDetected = true;
                        robot.Mineral.setPower(0);
                        break;
                    }
                    //telemetry.addData("Gold count is" + countGold,"Silver count is" + countSilver);
                    //telemetry.update();
                    if(isGold().equals("GOLD MINERAL"))
                    {
                        countGold++;
                    }
                    else if(isGold().equals("SILVER MINERAL"))
                    {
                        countSilver++;
                    }
                    telemetry.addData("Gold count is" + countGold, "Silver count is" + countSilver, "The Servo has moved for " + milsdone);
                    telemetry.update();
                    if(i < 2 && isGold().equals("NO MINERAL"))
                    {
                        telemetry.addData("ABOUT TO MOVE FORWARD",i);
                        telemetry.update();
                        //sleep(1000);
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(0);
                        sleep(1000);
                        robot.Mineral.setPower(1);
                        sleep(500);
                        telemetry.addData("I is",i);
                        telemetry.addData("DONE MOVING FORWARD",i);
                        telemetry.update();
                        milsdone += 500;
                        robot.Mineral.setPower(0);
                    }
                    if(countGold > 1)
                    {
                        telemetry.addData("CHECK","2");
                        telemetry.update();
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(1);
                        sleep(3500-milsdone);
                        robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                        robot.Mineral.setPower(1);
                        sleep(2800);
                        goldDetected = true;
                        robot.Mineral.setPower(0);
                        break;
                    }
                    //sleep(5000);
                }
                //telemetry.addData("Gold count is" + countGold,"Silver count is" + countSilver);
                //telemetry.update();
                if(countGold > 0 && goldDetected == false)
                {
                    robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                    robot.Mineral.setPower(1);
                    sleep(3500-milsdone);
                    robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                    robot.Mineral.setPower(1);
                    sleep(2800);
                    robot.Mineral.setPower(0);
                }
                else
                {
                    robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                    robot.Mineral.setPower(1);
                    sleep(milsdone);
                    robot.Mineral.setPower(0);
                }
                goldDetected = true;
            }


            /*
            if(goldDetected == false) {
                String x = isGold();
                telemetry.addData(x, "CHECK");
                if (x.equals("GOLD MINERAL")) {
                    goldDetected = true;
                    robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                    robot.Mineral.setPower(1);
                    sleep(3500);
                    robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                    robot.Mineral.setPower(1);
                    sleep(3000);
                }
                robot.Mineral.setPower(0);
            }*/
        }

    }
    public void GoldDetection() throws InterruptedException {
        boolean goldDetected =  false;
        int countGold = 0;
        int countSilver = 0;
        int milsdone = 0;
        if(opModeIsActive()) {
            if (goldDetected == false) {
                for (int i = 0; i < 3; i++) {
                    if (countGold > 1) {
                        telemetry.addData("CHECK", "1");
                        telemetry.update();
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(1);
                        sleep(3500 - milsdone);
                        robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                        robot.Mineral.setPower(1);
                        sleep(2800);
                        goldDetected = true;
                        robot.Mineral.setPower(0);
                        break;
                    }
                    //telemetry.addData("Gold count is" + countGold,"Silver count is" + countSilver);
                    //telemetry.update();
                    if (isGold().equals("GOLD MINERAL")) {
                        countGold++;
                    } else if (isGold().equals("SILVER MINERAL")) {
                        countSilver++;
                    }
                    telemetry.addData("Gold count is" + countGold, "Silver count is" + countSilver, "The Servo has moved for " + milsdone);
                    telemetry.update();
                    if (i < 2 && isGold().equals("NO MINERAL")) {
                        telemetry.addData("ABOUT TO MOVE FORWARD", i);
                        telemetry.update();
                        //sleep(1000);
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(0);
                        sleep(1000);
                        robot.Mineral.setPower(1);
                        sleep(500);
                        telemetry.addData("I is", i);
                        telemetry.addData("DONE MOVING FORWARD", i);
                        telemetry.update();
                        milsdone += 500;
                        robot.Mineral.setPower(0);
                    }
                    if (countGold > 1) {
                        telemetry.addData("CHECK", "2");
                        telemetry.update();
                        robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                        robot.Mineral.setPower(1);
                        sleep(3500 - milsdone);
                        robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                        robot.Mineral.setPower(1);
                        sleep(2800);
                        goldDetected = true;
                        robot.Mineral.setPower(0);
                        break;
                    }
                    //sleep(5000);
                }
                //telemetry.addData("Gold count is" + countGold,"Silver count is" + countSilver);
                //telemetry.update();
                if (countGold > 0 && goldDetected == false) {
                    robot.Mineral.setDirection(CRServo.Direction.FORWARD);
                    robot.Mineral.setPower(1);
                    sleep(3500 - milsdone);
                    robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                    robot.Mineral.setPower(1);
                    sleep(2800);
                    robot.Mineral.setPower(0);
                } else {
                    robot.Mineral.setDirection(CRServo.Direction.REVERSE);
                    robot.Mineral.setPower(1);
                    sleep(milsdone);
                    robot.Mineral.setPower(0);
                }
                goldDetected = true;
            }
        }
    }
    private void moveToPos(double leftPower, double rightPower, double inL, double inR) {
        robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.FrontLeft.setPower(leftPower);
        robot.BackLeft.setPower(leftPower);
        robot.FrontRight.setPower(rightPower);
        robot.BackRight.setPower(rightPower);

        int targetL = convertToTicks(inL);
        int targetR = convertToTicks(inR);

        while(opModeIsActive()) {
            telemetry.addData("encoder-frontLeft: ", robot.FrontLeft.getCurrentPosition());
            telemetry.addData("encoder-backLeft: ", robot.BackLeft.getCurrentPosition());
            telemetry.addData("encoder-frontRight: ", robot.FrontRight.getCurrentPosition());
            telemetry.addData("encoder-backRight: ", robot.BackRight.getCurrentPosition());
            telemetry.update();

            boolean leftcheck = false;
            boolean rightcheck = false;
            if(targetL > 0) {
                if(robot.FrontLeft.getCurrentPosition() > targetL || robot.BackLeft.getCurrentPosition() > targetL) {
                    robot.FrontLeft.setPower(0);
                    robot.BackLeft.setPower(0);
                    leftcheck = true;
                }
            } else {
                if(robot.FrontLeft.getCurrentPosition() < targetL || robot.BackLeft.getCurrentPosition() < targetL) {
                    robot.FrontLeft.setPower(0);
                    robot.BackLeft.setPower(0);
                    leftcheck = true;
                }
            }
            if(targetR > 0) {
                if(robot.FrontRight.getCurrentPosition() > targetR || robot.BackRight.getCurrentPosition() > targetR) {
                    robot.FrontRight.setPower(0);
                    robot.BackRight.setPower(0);
                    rightcheck = true;
                }
            } else {
                if(robot.FrontRight.getCurrentPosition() < targetR || robot.BackRight.getCurrentPosition() < targetR) {
                    robot.FrontRight.setPower(0);
                    robot.BackRight.setPower(0);
                    rightcheck = true;
                }
            }

            if(leftcheck && rightcheck) {
                break;
            }
        }

        robot.FrontLeft.setPower(0);
        robot.BackLeft.setPower(0);
        robot.FrontRight.setPower(0);
        robot.BackRight.setPower(0);
    }

    public void SigmaDrive(double leftPower,double rightPower, double lInch,double rInch)
    {
        robot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int leftTarget = convertToTicks(lInch) + robot.FrontLeft.getCurrentPosition();
        int rightTarget = convertToTicks(rInch) + robot.FrontRight.getCurrentPosition();

        robot.FrontLeft.setTargetPosition(leftTarget);
        robot.FrontRight.setTargetPosition(rightTarget);
        robot.BackLeft.setTargetPosition(leftTarget);
        robot.BackRight.setTargetPosition(rightTarget);

        robot.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.FrontLeft.setPower(leftPower);
        robot.FrontRight.setPower(rightPower);
        robot.BackLeft.setPower(leftPower);
        robot.BackRight.setPower(rightPower);

        while (opModeIsActive() &&
                (robot.FrontLeft.isBusy() && robot.FrontRight.isBusy())) {

            if (Math.abs(robot.FrontLeft.getCurrentPosition()
                    - robot.FrontLeft.getTargetPosition()) <= 10) {
                break;
            } else if (Math.abs(robot.FrontRight.getCurrentPosition()
                    - robot.FrontRight.getTargetPosition()) <= 10) {
                break;
            }


            telemetry.addData("Front Right: ", robot.FrontRight.getCurrentPosition());
            telemetry.addData("Front Left: ", robot.FrontLeft.getCurrentPosition());
            telemetry.addData("Back Right: ", robot.BackRight.getCurrentPosition());
            telemetry.addData("Back Left: ", robot.BackLeft.getCurrentPosition());
        }

        robot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    protected String isGold() throws InterruptedException {

        // values is a reference to the hsvValues array.
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        String ifGold = "NO MINERAL";
        boolean check = false;
        // bPrevState and bCurrState keep track of the previous and current state of the button
        /*boolean bPrevState = false;
        boolean bCurrState = false;


        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (robot.ColorSensor instanceof SwitchableLight) {
            ((SwitchableLight)robot.ColorSensor).enableLight(true);
        }

        // Wait for the start button to be pressed.

        // Loop until we are asked to stop*/
        while (check == false) {/*
            // Check the status of the x button on the gamepad
            //bCurrState = gamepad1.x;

            // If the button state is different than what it was, then act
            if (bCurrState != bPrevState) {
                // If the button is (now) down, then toggle the light
                if (bCurrState) {
                    if (robot.ColorSensor instanceof SwitchableLight) {
                        SwitchableLight light = (SwitchableLight)robot.ColorSensor;
                        light.enableLight(!light.isLightOn());
                    }
                }
            }
            bPrevState = bCurrState;
*/
            // Read the sensor
            NormalizedRGBA colors = robot.ColorSensor.getNormalizedColors();

            /** Use telemetry to display feedback on the driver station. We show the conversion
             * of the colors to hue, saturation and value, and display the the normalized values
             * as returned from the sensor.
             * @see <a href="http://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html">HSV</a>*/

            Color.colorToHSV(colors.toColor(), hsvValues);
            telemetry.addLine()
                    .addData("H", "%.3f", hsvValues[0])
                    .addData("S", "%.3f", hsvValues[1])
                    .addData("V", "%.3f", hsvValues[2]);
            /*telemetry.addLine()
                    .addData("a", "%.3f", colors.alpha)
                    .addData("r", "%.3f", colors.red)
                    .addData("g", "%.3f", colors.green)
                    .addData("b", "%.3f", colors.blue);*/
            if(hsvValues[0] < 120 && hsvValues[0] >= 15)
            {
                ifGold = "GOLD MINERAL";
                //System.out.println(ifGold);
            }
            else if(hsvValues[0] >= 120 && hsvValues[0] <= 180)
            {
                ifGold = "SILVER MINERAL";
            }
            else
            {
                ifGold = "NO MINERAL";
            }
            check = true;
            if(ifGold.equals("GOLD MINERAL"))
            telemetry.addData("IT IS GOLD",check);
            else if(ifGold.equals("SILVER MINERAL")){
                telemetry.addData("IT IS SILVER",check);
            }
            else
            {
                telemetry.addData("IT CAN'T SENSE",check);
            }
            telemetry.update();
        }
        return ifGold;
    }
    /*public ArrayList<Double> Vuforia()
    {
        ArrayList<Double> Values = new ArrayList<Double>();
        while (opModeIsActive()) {
            // check all the trackable target to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();
        }
        return Values;
    }*/
    public int convertToTicks(double in)
    {
        return (int) Math.round(in * 1440/12.556);
    }

}
