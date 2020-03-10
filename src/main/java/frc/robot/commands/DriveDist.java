/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive.SENSOR_TYPES;

public class DriveDist extends CommandBase {
  /**
   * Creates a new DriveDist.
   */
  double dist;
  public DriveDist(double dist) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
    this.dist = dist;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drive.enable();
    RobotContainer.drive.setCurrentSensor(SENSOR_TYPES.DISTANCE);
    RobotContainer.drive.resetDistance();
    RobotContainer.drive.setSetpoint(dist*Constants.DRIVE_TICKS_PER_METER);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.drive.drive.feedWatchdog();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.drive.getController().atSetpoint();
  }
}