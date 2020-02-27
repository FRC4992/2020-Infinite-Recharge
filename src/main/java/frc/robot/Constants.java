/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Pneumatics
    public static final int
    SHIFTER_FORWARD = 0,
    SHIFTER_REVERSE = 1;

    // Motors
    public static final int
    TOP_LEFT_DRIVE = 4,
    BOTTOM_LEFT_DRIVE = 1,
    TOP_RIGHT_DRIVE = 3,
    BOTTOM_RIGHT_DRIVE = 2,
    INTAKE_MOTOR = 5,
    INDEX_MOTOR = 6,
    LEFT_SHOOT_MOTOR = 7,
    RIGHT_SHOOT_MOTOR = 8,
    TELESCOPE_MOTOR = 9,
    LEFT_WINCH = 10,
    RIGHT_WINCH = 11,
    PANEL_MOTOR = 12;

    // LEDs
    public static final int
    LED_PWM = 0,
    LED_LENGTH = 60,
    LED_MIDDLE_START = 19,
    LED_MIDDLE_END = 38;

    // Joystick
    public static final int
    STICK = 0;

    // Sensors
}
