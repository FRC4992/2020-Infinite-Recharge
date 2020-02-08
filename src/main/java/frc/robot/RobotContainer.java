/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static Drive drive = new Drive();
  public static Joystick driveStick = new Joystick(Constants.STICK);
  private static NetworkTableInstance tableInstance;
  private static NetworkTable limelight;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    tableInstance = NetworkTableInstance.getDefault();
    limelight = tableInstance.getTable("limelight");
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
    JoystickButton shiftDown = new JoystickButton(driveStick, 5);
    shiftDown.whenPressed(() -> {
      drive.shifter.set(DoubleSolenoid.Value.kForward);
    });
    JoystickButton shiftUp = new JoystickButton(driveStick, 6);
    shiftUp.whenPressed(() -> {
      drive.shifter.set(DoubleSolenoid.Value.kReverse);
    });
  }
}
