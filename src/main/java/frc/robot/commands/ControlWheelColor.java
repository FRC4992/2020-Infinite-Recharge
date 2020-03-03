/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class ControlWheelColor extends CommandBase {
  /**
   * Creates a new SpinControlWheelToColor.
   */
  public ControlWheelColor() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.wheelSpinner);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // RobotContainer.wheelSpinner.setSetpoint(Constants.TICKS_PER_SEGMENT+RobotContainer.wheelSpinner.getPos());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.wheelSpinner.motor.set(0.4);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.wheelSpinner.motor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.wheelSpinner.onColor(RobotContainer.wheelSpinner.getDesiredColor());
  }
}
