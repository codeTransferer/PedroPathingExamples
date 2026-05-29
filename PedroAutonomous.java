package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.pedro.PedroCommands.*;
import static com.pedropathing.ivy.groups.Groups.*;

import static java.lang.Thread.sleep;

@Autonomous(name = "orthisone", group = "Examples")
public class PedroAutonomous extends OpMode {

    private DcMotor spinny = null;
    private CRServo s1;
    private CRServo s2;
    private Follower follower;
    // Control Points

    // Location Points
    private final Pose point1 = new Pose(TileAddons.convertToX(2.5), TileAddons.convertToY(0.5), Math.toRadians(90)); // Starts here
    private final Pose point2 = new Pose(TileAddons.convertToX(0.5), TileAddons.convertToY(1.5), Math.toRadians(180));
    private final Pose point3 = new Pose(TileAddons.convertToX(4.5), TileAddons.convertToY(2), Math.toRadians(0));
    private final Pose point4 = new Pose(TileAddons.convertToX(5.5), TileAddons.convertToY(2), Math.toRadians(0));
    private final Pose point5 = new Pose(TileAddons.convertToX(3.0), TileAddons.convertToY(3.5), Math.toRadians(180));
    private final Pose point6 = new Pose(TileAddons.convertToX(0.5), TileAddons.convertToY(3.5), Math.toRadians(180));

    private PathChain move1, move2, move3, move4, move5, move6;
    public void buildPaths() {
        move1 = follower.pathBuilder()
                .addPath(new BezierLine(point1, point2))
                .setLinearHeadingInterpolation(point1.getHeading(), point2.getHeading())
                .build();
        move2 = follower.pathBuilder()
                .addPath(new BezierLine(point2, point3))
                .setLinearHeadingInterpolation(point2.getHeading(), point3.getHeading())
                .build();
        move3 = follower.pathBuilder()
                .addPath(new BezierLine(point3, point4))
                .setLinearHeadingInterpolation(point3.getHeading(), point4.getHeading())
                .build();
        move4 = follower.pathBuilder()
                .addPath(new BezierLine(point4, point5))
                .setLinearHeadingInterpolation(point4.getHeading(), point5.getHeading())
                .build();
        move5 = follower.pathBuilder()
                .addPath(new BezierLine(point5, point6))
                .setLinearHeadingInterpolation(point5.getHeading(), point6.getHeading())
                .build();
        move6 = follower.pathBuilder()
                .addPath(new BezierLine(point6, point1))
                .setLinearHeadingInterpolation(point6.getHeading(), point1.getHeading())
                .build();
    }

    public Command autoRoutine() {
        return sequential(
                // Commands.waitMs(1000), Useful command
                setSpinnyPower(0.40),
                follow(follower, move1, true), // intakes ball
                Commands.waitMs(1000),
                follow(follower, move2, true),
                Commands.waitMs(100),
                follow(follower,move3,true),  // intakes ball
                Commands.waitMs(1000),
                follow(follower,move4,true),
                Commands.waitMs(100),
                follow(follower,move5,true), // intakes ball
                Commands.waitMs(1000),
                follow(follower,move6,true), // returns to start
                Commands.waitMs(100),
                setSpinnyPower(0)
                );
    }

    public Command setSpinnyPower(double p) {
        return instant(() -> spinny.setPower(p));
    }
    public Command servo1On(double p){

        return instant(() -> s1.setPower(p));
    }
    public Command servo2On(double p){

        return instant(() -> s2.setPower(p));
    }
    @Override
    public void init() {
        TileAddons.setXRange(0, 142);
        TileAddons.setYRange(0, 144);
        TileAddons.flipXandY();
        //TileAddons.flipX();
        Scheduler.reset();
        follower = Constants.createFollower(hardwareMap);
        s1 = hardwareMap.get(CRServo.class,"servo1");
        s2 = hardwareMap.get(CRServo.class,"servo2");
        buildPaths();
        // -------------------------------------------------------------------------- Set Motor Name and Direction
        spinny = hardwareMap.get(DcMotor.class, "spinny ");
        spinny.setDirection(DcMotor.Direction.REVERSE);
        // --------------------------------
        follower.setStartingPose(point1);

    }
    @Override
    public void init_loop() {}
    @Override
    public void start() {

        schedule(autoRoutine());
    }
    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        // Feedback to Driver Hub for debugging
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
    @Override
    public void stop() {}
}
