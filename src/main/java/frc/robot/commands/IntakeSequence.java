/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Indexer;

public class IntakeSequence extends CommandBase {
  /**
   * Creates a new IntakeSequence.
   */
  boolean ballEntered = false;
  public IntakeSequence() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.indexer);
    addRequirements(RobotContainer.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ballEntered = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.indexer.getController().atSetpoint() || RobotContainer.indexer.tof.getRange()<80){
      RobotContainer.intake.intake();
    }else{
      RobotContainer.intake.stop();
    }

    if(!ballEntered && RobotContainer.indexer.seeBall()){
      ballEntered = true;
      System.out.println("Cycle!");
      Indexer.ballCount++;
      if(Indexer.ballCount<5){
        RobotContainer.indexer.cycleBalls();
      }
    }


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.intake.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Indexer.ballCount>=5;
  }
}
