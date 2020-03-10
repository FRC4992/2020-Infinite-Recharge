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

public class SpinControlWheel extends CommandBase {
  /**
   * Creates a new SpinControlWheelToPosition.
   */
  int rotations;
  int rotationsRemaining;
  WHEEL_COLORS prevColor;
  public SpinControlWheel(int rotations) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.wheelSpinner);
    this.rotations = rotations;
    rotationsRemaining = 8*rotations;
    prevColor = RobotContainer.wheelSpinner.currentColor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.wheelSpinner.extend();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!RobotContainer.wheelSpinner.currentColor.equals(prevColor.color)){
      rotationsRemaining--;
    }
    prevColor = RobotContainer.wheelSpinner.currentColor;
    RobotContainer.wheelSpinner.motor.set(0.4);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.wheelSpinner.motor.set(0);
    RobotContainer.wheelSpinner.retract();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return rotationsRemaining<=0;
  }
}
