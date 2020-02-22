/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {
  
  /* DRIVETRAIN */
/***********************************************************************************************************************************************/
    // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
    public PWMVictorSPX frontLeftMotor = new PWMVictorSPX(1);
    public PWMVictorSPX backLeftMotor = new PWMVictorSPX(2);
    public SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
  
    // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
    public PWMVictorSPX frontRightMotor = new PWMVictorSPX(3);
    public PWMVictorSPX backRightMotor = new PWMVictorSPX(4);
    public SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontRightMotor, backRightMotor);

    // INSTANTIATE DRIVETRAIN
    public DifferentialDrive driveTrain = new DifferentialDrive(leftDrive, rightDrive);
/***********************************************************************************************************************************************/

  /* INTAKE, STORAGE, LOADER, LATCH, AND FORTUNE */
/***********************************************************************************************************************************************/
    // INSTANTIATE INTAKE MOTOR
    public PWMVictorSPX intakeMotor = new PWMVictorSPX(7);

    // INSTANTIATE STORAGE MOTOR
    public PWMTalonSRX storageMotor = new PWMTalonSRX(8);
  
    // INSTANTIATE LATCH MOTOR
    public PWMVictorSPX latchMotor = new PWMVictorSPX(100);

    // INSTANTIATE FORTUNE MOTOR
    public PWMVictorSPX fortuneMotor = new PWMVictorSPX(100);
/***********************************************************************************************************************************************/

  /* SHOOTER AND LIFT */
/***********************************************************************************************************************************************/
    // INSTANTIATE SHOOTER MOTORS AND SHOOTER
    public PWMSpeedController rightShooterMotor = new PWMVictorSPX(5);
    public PWMSpeedController leftShooterMotor = new PWMTalonSRX(6);
    public DifferentialDrive shooter = new DifferentialDrive(leftShooterMotor, rightShooterMotor);

    // INSTANTIATE LIFT MOTORS AND LIFT
    public PWMSpeedController leftLiftMotor = new PWMVictorSPX(0);
    public PWMSpeedController rightLiftMotor = new PWMTalonSRX(9);
    public DifferentialDrive lift = new DifferentialDrive(leftShooterMotor, rightShooterMotor);
/***********************************************************************************************************************************************/

  /* CONTROLLER */
/***********************************************************************************************************************************************/
    // INSTANTIATE XBOX CONTROLLER
    public XboxController controller = new XboxController(0);

    // INSTANTIATE JOYSTICK AXES VARIABLES
    public static int leftStickY = 1;
    public static int rightStickY = 5;
    public static int leftTrigger = 2;
    public static int rightTrigger = 3;
    
    // INSTANTIATE JOYSTICK BUTTONS
    public JoystickButton aButton = new JoystickButton(controller, 1);
    public JoystickButton bButton = new JoystickButton(controller, 2);
    public JoystickButton xButton = new JoystickButton(controller, 3);
    public JoystickButton yButton = new JoystickButton(controller, 4);
    public JoystickButton leftBumper = new JoystickButton(controller, 5);
    public JoystickButton rightBumper = new JoystickButton(controller, 6);
/***********************************************************************************************************************************************/

  /* PNEUMANTICS */  
/***********************************************************************************************************************************************/
    // INSTANTIATE COMPRESSOR
    public Compressor compressor = new Compressor();

    // INSTANTIATE LOADER SOLENOID
    public Solenoid loader = new Solenoid(2,3);
/***********************************************************************************************************************************************/

@Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
