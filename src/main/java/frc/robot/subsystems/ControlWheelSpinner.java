/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlWheelSpinner extends SubsystemBase {
  /**
   * Creates a new ControlWheelSpinner.
   */
  public WPI_TalonSRX motor;
  public DoubleSolenoid piston;
  ColorSensorV3 colorSens = new ColorSensorV3(I2C.Port.kOnboard);
  ColorMatch matcher = new ColorMatch();
  public WHEEL_COLORS currentColor = WHEEL_COLORS.NOT_FOUND;

  public static enum WHEEL_COLORS {
    BLUE(ColorMatch.makeColor(0.135, 0.412, 0.44), "Blue"), GREEN(ColorMatch.makeColor(0.19, 0.55, 0.25), "Green"),
    RED(ColorMatch.makeColor(0.49, 0.35, 0.15), "Red"), YELLOW(ColorMatch.makeColor(0.32, 0.55, 0.12), "Yellow"),
    NOT_FOUND(ColorMatch.makeColor(0, 0, 0), "Not Found");

    public Color color;
    private String s;

    private WHEEL_COLORS(Color c, String s) {
      this.color = c;
      this.s = s;
    }

    public boolean equals(Color c) {
      return (color.red == c.red && color.green == c.green && color.blue == c.blue);
    }

    public String toString() {
      return this.s;
    }
  }

  public String[] colorSequence = new String[] { "Red", "Green", "Blue", "Yellow" };

  public ControlWheelSpinner() {
    motor = new WPI_TalonSRX(Constants.PANEL_MOTOR);
    piston = new DoubleSolenoid(Constants.SPINNER_FORWARD, Constants.SPINNER_REVERSE);
    for (WHEEL_COLORS c : WHEEL_COLORS.values()) {
      matcher.addColorMatch(c.color);
    }
    retract();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed) {
    motor.set(speed);
  }

  public void extend(){
    piston.set(DoubleSolenoid.Value.kReverse);
  }

  public void retract(){
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void updateColor() {
    SmartDashboard.putNumber("R", colorSens.getColor().red);
    SmartDashboard.putNumber("G", colorSens.getColor().green);
    SmartDashboard.putNumber("B", colorSens.getColor().blue);
    Color prediction = matcher.matchClosestColor(colorSens.getColor()).color;
    for (WHEEL_COLORS c : WHEEL_COLORS.values()) {
      if (c.equals(prediction)) {
        currentColor = c;
      }
    }
    SmartDashboard.putString("Sensed Color", currentColor.toString());
  }

  public WHEEL_COLORS getDesiredColor() {
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData) {
      case "R":
        return WHEEL_COLORS.BLUE;
      case "G":
        return WHEEL_COLORS.YELLOW;
      case "B":
        return WHEEL_COLORS.RED;
      case "Y":
        return WHEEL_COLORS.GREEN;
      }
    }
    return WHEEL_COLORS.NOT_FOUND;
  }

  public boolean onColor(WHEEL_COLORS c) {
    return currentColor.equals(c.color);
  }
}
