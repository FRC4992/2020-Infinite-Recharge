/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive.SENSOR_TYPES;

public class TurnToAngle extends CommandBase {
  /**
   * Creates a new TurnToAngle.
   */
  public static enum TURN_TYPE {
    ABSOLUTE, RELATIVE
  }

  TURN_TYPE turnType;
  double setpoint;
  double startingValue = 0;

  public TurnToAngle(TURN_TYPE type, double setpoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
    this.turnType = type;
    this.setpoint = setpoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drive.enable();
    RobotContainer.drive.setCurrentSensor(SENSOR_TYPES.ROTATION);
    startingValue = RobotContainer.drive.getMeasurement();
    switch (turnType) {
    case ABSOLUTE:
      RobotContainer.drive.setSetpoint(setpoint);
      break;
    case RELATIVE:
    RobotContainer.drive.setSetpoint(setpoint+startingValue);
      break;
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.drive.drive.feedWatchdog();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.drive.getController().atSetpoint();
  }
}
