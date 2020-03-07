/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IntakeSequence;
import frc.robot.commands.PowercellAlign;
import frc.robot.commands.SetTilterTicks;
import frc.robot.commands.ShooterAlign;
import frc.robot.commands.TempShoot;
import frc.robot.commands.ToggleShift;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.TurnToAngle.TURN_TYPE;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoDistAndCenter;
import frc.robot.commands.DriveDist;
import frc.robot.subsystems.Drive;
import frc.robot.commands.WinchUp;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.Telescope;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static Indexer indexer = new Indexer();
  public static Intake intake = new Intake();
  public static Joystick stick;
  public static Shooter shooter = new Shooter();
  public static Drive drive = new Drive();
  public ArcadeDrive arcadeDriveCommand = new ArcadeDrive();
  public static Joystick driveStick = new Joystick(Constants.STICK);
  private static NetworkTableInstance tableInstance;
  private static NetworkTable limelight;
  public static Winch winch = new Winch();
  public static Telescope telescope = new Telescope();


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    stick = new Joystick(0);
    configureButtonBindings();
    tableInstance = NetworkTableInstance.getDefault();
    limelight = tableInstance.getTable("limelight");
    drive.setDefaultCommand(new ArcadeDrive());
    
  }

  public static boolean foundTarget() {
    return limelight.getEntry("tv").getDouble(0) == 1;
  }

  public static double limelightDist() {
    return limelight.getEntry("ta").getDouble(0);
  }

  public static double limelightX() {
    return -limelight.getEntry("tx").getDouble(0);
  }
  public static double limelightY(){
    return limelight.getEntry("ty").getDouble(0);
  }

  public static void setPipleline(int index) {
    limelight.getEntry("pipeline").setDouble(index);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton full = new JoystickButton(stick,3);
    full.whenHeld(new IntakeSequence());
    JoystickButton shoot = new JoystickButton(stick,2);
    shoot.whenHeld(new TempShoot());

    JoystickButton toggleShift = new JoystickButton(stick,7);
    toggleShift.whenPressed(new ToggleShift());
    

    // JoystickButton shiftUp = new JoystickButton(stick, 11);
    // shiftUp.whenPressed(()->{drive.shifter.set(DoubleSolenoid.Value.kForward);});

    // JoystickButton shiftDown = new JoystickButton(stick, 12);
    // shiftUp.whenPressed(()->{drive.shifter.set(DoubleSolenoid.Value.kReverse);});

    JoystickButton powerAlign = new JoystickButton(stick,5);
    // powerAlign.whenPressed(new SetTilterTicks(0));
    powerAlign.whenHeld(new PowercellAlign());

    JoystickButton shootAlign = new JoystickButton(stick,6);
    shootAlign.whenHeld(new ShooterAlign());

    Joystick secondStick = new Joystick(1);
    
    JoystickButton shootSpeedUp = new JoystickButton(secondStick, 8);
    JoystickButton shootSpeedDown = new JoystickButton(secondStick, 7);
    shootSpeedUp.whenPressed(()->{shooter.tempRPM+=100;System.out.println(shooter.tempRPM);});
    shootSpeedDown.whenPressed(()->{shooter.tempRPM-=100;System.out.println(shooter.tempRPM);});

    JoystickButton autoDistAndCenter = new JoystickButton(secondStick, 1);
    autoDistAndCenter.whenHeld(new AutoDistAndCenter());

    JoystickButton driveOneMeter = new JoystickButton(secondStick,2);
    driveOneMeter.whenHeld(new DriveDist(1));

    JoystickButton rotate90 = new JoystickButton(secondStick,3);
    rotate90.whenHeld(new TurnToAngle(TURN_TYPE.RELATIVE, 90));

    JoystickButton winch = new JoystickButton(secondStick, 5);
    winch.whenHeld(new WinchUp());
    // winch.whenPressed(new TelescopeDown());

    JoystickButton extendTelescope = new JoystickButton(secondStick, 6);
    // extendTelescope.whenHeld(new TelescopeUp());
  }
}
