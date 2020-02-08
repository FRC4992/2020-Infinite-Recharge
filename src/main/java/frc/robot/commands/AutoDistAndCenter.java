/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.LEDRunner.AnimationMode;
import frc.robot.subsystems.Drive.SENSOR_TYPES;

public class AutoDistAndCenter extends CommandBase {
  /**
   * Creates a new AutoDistAndCenter.
   */

  public AutoDistAndCenter() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drive.setCurrentSensor(SENSOR_TYPES.LIMELIGHT_BOTH);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xMeasurement = RobotContainer.limelightX();
    double rotationSpeed = RobotContainer.drive.autoRotate.calculate(xMeasurement, 0);
    double yMeasurement = RobotContainer.limelightDist();
    double driveSpeed = RobotContainer.drive.autoDist.calculate(yMeasurement, 2);
    if (RobotContainer.foundTarget()) {
      RobotContainer.drive.drive.arcadeDrive(driveSpeed, rotationSpeed, false);
    } else {
      RobotContainer.drive.drive.arcadeDrive(0, 0);
    }

    // RobotContainer.drive.displayAlign(255, 0, 0, AnimationMode.SEARCHING_RED);

    // System.out.println("running with values: "+driveSpeed+", "+rotationSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.drive.autoRotate.atSetpoint() && RobotContainer.drive.autoDist.atSetpoint();
  }
}
