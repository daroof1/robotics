package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

public class HardwareSigma2019 {

    public DcMotor BackLeft = null;
    public DcMotor FrontLeft = null;
    public DcMotor BackRight = null;
    public DcMotor FrontRight = null;
    public DcMotor Left = null;
    public DcMotor Right = null;
    public Servo Sorter = null;
    public CRServo Intake = null;
    public DcMotor HangingMotor = null;
    public DcMotor SpoolMotor = null;
    public ServoController ServoController = null;
    public BNO055IMU imu = null;
    Orientation angles = null;
    Acceleration gravity = null;

    HardwareMap hardwareMap = null;
    ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap hwMap) throws InterruptedException
    {
        hardwareMap = hwMap;
        //Initializing the motors
        //ServoController = hardwareMap.servoController.get("Servo Controller");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        Left = hardwareMap.dcMotor.get("Left");
        Right = hardwareMap.dcMotor.get("Right");
        Sorter = hardwareMap.servo.get("Sorter");
        Intake = hardwareMap.crservo.get("Intake");
        HangingMotor = hardwareMap.dcMotor.get("Hanging");
        SpoolMotor = hardwareMap.dcMotor.get("SpoolMotor");
        // Switch the directions on the motors if not using AndyMark
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.REVERSE);
        //HangingMotor.setDirection(DcMotor.Direction.FORWARD);\
        //VexMotor.setPower(1);

        FrontLeft.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        Right.setPower(0);
        Left.setPower(0);
        SpoolMotor.setPower(0);
        Intake.setPower(0);


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public double getTime(){
        return period.time();
    }
    public void startTime(){
        period.reset();
        period.startTime();
    }
}
