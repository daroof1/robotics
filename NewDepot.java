package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "NewDepot",group = "LinearOpMode")
public class NewDepot extends CommonUtils {


    @Override
    public void runOpMode() throws InterruptedException {
        craterSide = false;
        Auto();
    }
}
