/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ControlWheelSpinner.WHEEL_COLORS;

public class SpinControlWheelToPosition extends CommandBase {
  /**
   * Creates a new SpinControlWheelToPosition.
   */
  int rotations;
  int rotationsRemaining;
  WHEEL_COLORS prevColor;
  public SpinControlWheelToPosition(int rotations) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.spinner);
    this.rotations = rotations;
    rotationsRemaining = 8*rotations;
    prevColor = RobotContainer.spinner.currentColor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.spinner.updateColor();
    if(!RobotContainer.spinner.currentColor.equals(prevColor.color)) {
      rotationsRemaining--;
    }
    prevColor = RobotContainer.spinner.currentColor;
    RobotContainer.spinner.setSpeed(0.4);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return rotationsRemaining<=0;
  }
}
