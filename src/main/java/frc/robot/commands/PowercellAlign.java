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

public class PowercellAlign extends CommandBase {
  /**
   * Creates a new PowercellAlign.
   */
  public PowercellAlign() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drive.enable();
    RobotContainer.drive.setCurrentSensor(SENSOR_TYPES.POWERCELL);
    RobotContainer.drive.setSetpoint(0);
    Robot.ledRunner.setAnimation(AnimationMode.OFF);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // RobotContainer.drive.displayAlign(255, 175, 0,
    // AnimationMode.SEARCHING_YELLOW);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // System.out.println("AutoCenter ended");
    RobotContainer.drive.stop();
    Robot.ledRunner.setAnimation(AnimationMode.FULL_RAINBOW);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
