/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends TimedRobot {

  /* DRIVETRAIN */
/***********************************************************************************************************************************************/
    // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
    public SpeedController frontLeftMotor = new PWMVictorSPX(1);
    public SpeedController backLeftMotor = new PWMVictorSPX(2);
    public SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
  
    // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
    public SpeedController frontRightMotor = new PWMVictorSPX(3);
    public SpeedController backRightMotor = new PWMVictorSPX(4);
    public SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontRightMotor, backRightMotor);
/***********************************************************************************************************************************************/

  /* INTAKE, STORAGE, LOADER, LATCH, AND FORTUNE */
/***********************************************************************************************************************************************/
    // INSTANTIATE INTAKE MOTOR
    public PWMVictorSPX intakeMotor = new PWMVictorSPX(7);

    // INSTANTIATE STORAGE MOTOR
    public PWMVictorSPX storageMotor = new PWMVictorSPX(6);
  
    // INSTANTIATE LATCH MOTOR
    //public PWMVictorSPX latchMotor = new PWMVictorSPX(15);

    // INSTANTIATE FORTUNE MOTOR
    //public PWMVictorSPX fortuneMotor = new PWMVictorSPX(16);
/***********************************************************************************************************************************************/

  /* SHOOTER AND LIFT */
/***********************************************************************************************************************************************/
    // INSTANTIATE SHOOTER MOTORS AND SHOOTER
    public PWMSpeedController leftShooterMotor = new PWMVictorSPX(5);
    public PWMSpeedController rightShooterMotor = new Talon(8);

    // INSTANTIATE LIFT MOTORS AND LIFT
    //public PWMSpeedController leftLiftMotor = new PWMVictorSPX(0);
    //public PWMSpeedController rightLiftMotor = new PWMTalonSRX(9);
    //public DifferentialDrive lift = new DifferentialDrive(leftShooterMotor, rightShooterMotor);
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
    public DoubleSolenoid loader = new DoubleSolenoid(0, 1);
/***********************************************************************************************************************************************/

@Override
  // RUNS WHEN ROBOTS STARTS
  public void robotInit() {
    // TURN OFF ALL MOTORS
    leftDrive.set(0);
    rightDrive.set(0);
    leftShooterMotor.set(0);
    rightShooterMotor.set(0);
    
    // RESETS PISTON POSITIONS
    loader.set(Value.kReverse);
    compressor.clearAllPCMStickyFaults();
  }
  // RUNS ONCE WHEN AUTONOMOUS STARTS
  @Override
  public void autonomousInit() {
  }
  // RUNS DURING AUTONOMOUS
  @Override
  public void autonomousPeriodic() {
  }
  // RUNS ONCE WHEN TELEOP STARTS
  @Override
  public void teleopInit() {
  }
  // RUNS DURING TELEOP
  @Override
  public void teleopPeriodic() {

  /* DRIVETRAIN */
/***********************************************************************************************************************************************/
    // SETS VARIABLES AS AXES ON CONTROLLER
    double leftY = controller.getRawAxis(leftStickY);
    double rightY = controller.getRawAxis(rightStickY);
    double rightTrig = controller.getRawAxis(rightTrigger);

    // READS TRIGGER VALUES TO ADJUST SPEED OF DRIVETRAIN
    if (rightTrig > .5) {
      leftY = leftY*.9;
      rightY = rightY*.9;
    } else {
      leftY = leftY*.7;
      rightY = rightY*.7;
    }

    // SETS DRIVE SIDES TO AXES
    leftDrive.set(-leftY);
    rightDrive.set(rightY);
/***********************************************************************************************************************************************/

  /* INTAKE */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS BUMPER
    boolean leftBump = leftBumper.get();

    // READ LEFTBUMPER TO ACTIVATE INTAKE MOTOR
    if(leftBump == true) {
      intakeMotor.set(.9);
    } else {
      intakeMotor.set(0);
    }
/***********************************************************************************************************************************************/

  /* LOADER */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS ABUTTON
    boolean aBtn = aButton.get();

    // READ LEFTBUMPER TO ACTIVATE LOADER MOTOR
    if(aBtn == true) {
      storageMotor.set(-.4);
    } else {
      storageMotor.set(0);
    }
/***********************************************************************************************************************************************/

  /* SHOOTER */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS RIGHT BUMPER
    boolean rightBump = rightBumper.get();

    // READ LEFTBUMPER TO ACTIVATE SHOOTER
    if(rightBump == true) {
      leftShooterMotor.set(1);
      rightShooterMotor.set(1);
    } else {
      leftShooterMotor.set(0);
      rightShooterMotor.set(0);
    }
/***********************************************************************************************************************************************/

/* PNEUMATICS */
/**********************************************************************************************************************************************/
    // SETS BOOLEAN AS XBUTTON
    boolean xBtn = xButton.get();
    
    // READS X BUTTON TO FIRE SOLENOID
    if(xBtn == true) {
      loader.set(Value.kForward);
    } else { 
      loader.set(Value.kReverse);
    }
/***********************************************************************************************************************************************/

  }
  // RUNS ONCE WHEN TEST STARTS
  @Override
  public void testInit() {
  }
  // RUNS DURING TEST
  @Override
  public void testPeriodic() {
  }

}
