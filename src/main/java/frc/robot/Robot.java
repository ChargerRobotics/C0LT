/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends TimedRobot {

  /* DRIVETRAIN */
/***********************************************************************************************************************************************/
    // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
    public SpeedController frontLeftMotor = new PWMVictorSPX(3);
    public SpeedController backLeftMotor = new PWMVictorSPX(4);
    public SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
  
    // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
    public SpeedController frontRightMotor = new PWMVictorSPX(5);
    public SpeedController backRightMotor = new PWMVictorSPX(6);
    public SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontRightMotor, backRightMotor);
/***********************************************************************************************************************************************/

  /* INTAKE, STORAGE, LOADER, LATCH, AND FORTUNE */
/***********************************************************************************************************************************************/
    // INSTANTIATE INTAKE MOTOR
    public PWMVictorSPX intakeMotor = new PWMVictorSPX(7);

    // INSTANTIATE STORAGE MOTOR
    public PWMVictorSPX storageMotor = new PWMVictorSPX(1);
  
    // INSTANTIATE LATCH MOTOR
    /*public PWMVictorSPX latchMotor = new PWMVictorSPX(#);*/

    // INSTANTIATE FORTUNE MOTOR
    public PWMVictorSPX fortuneMotor = new PWMVictorSPX(8);
/***********************************************************************************************************************************************/

  /* SHOOTER AND LIFT */
/***********************************************************************************************************************************************/
    // INSTANTIATE SHOOTER MOTORS AND SHOOTER
    public PWMSpeedController leftShooterMotor = new PWMVictorSPX(0);
    public PWMSpeedController rightShooterMotor = new PWMTalonSRX(2);

    // INSTANTIATE LIFT MOTORS AND LIFT
    /*public PWMSpeedController leftLiftMotor = new PWMVictorSPX(#);
    public PWMSpeedController rightLiftMotor = new PWMVictorSPX(#);
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
    public DoubleSolenoid loader = new DoubleSolenoid(1, 0);

    // INSTANTIATE FORTUNE SOLENOID
    public DoubleSolenoid fortune = new DoubleSolenoid(3, 2);

    // INSTANTIATE LIMELIGHT TABLE
    public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
/***********************************************************************************************************************************************/

@Override
  // RUNS WHEN ROBOTS STARTS
  public void robotInit() {

    // TURN OFF ALL MOTORS
    leftDrive.set(0);
    rightDrive.set(0);
    leftShooterMotor.set(0);
    rightShooterMotor.set(0);
    
    // RESET PNEUMATICS
    loader.set(Value.kReverse);
    fortune.set(Value.kReverse);
    compressor.clearAllPCMStickyFaults();

    // TURN LIMELIGHT OFF
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
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
      leftY = leftY*.95;
      rightY = rightY*.95;
    } else {
      leftY = leftY*.65;
      rightY = rightY*.65;
    }

    // GETS RID OF CONTROLLER DRIFT
    if (leftY < .05) {
      if (leftY > -.05) {
        leftY = 0;
      }
    }
    if (rightY < .05) {
      if (rightY > -.05) {
        rightY = 0;
      }
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
      intakeMotor.set(.4);
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
      storageMotor.set(-.6);
    } else {
      storageMotor.set(0);
    }
/***********************************************************************************************************************************************/

    /* FORTUNE */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS YBUTTON
    boolean yBtn = yButton.get();

    // READ LEFTBUMPER TO ACTIVATE INTAKE MOTOR
    if(yBtn == true) {
      fortuneMotor.set(.6);
    } else {
      fortuneMotor.set(0);
    }
/***********************************************************************************************************************************************/

  /* SHOOTER */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS RIGHT BUMPER
    boolean rightBump = rightBumper.get();

    // READ LEFTBUMPER TO ACTIVATE SHOOTER
    if(rightBump == true) {
      leftShooterMotor.set(0.95);
      rightShooterMotor.set(0.95);
    } else {
      leftShooterMotor.set(0);
      rightShooterMotor.set(0);
    }
/***********************************************************************************************************************************************/

  /* PNEUMATICS */
/**********************************************************************************************************************************************/
    // SETS BOOLEAN AS XBUTTON
    boolean xBtn = xButton.get();
    
    // READS XBUTTON TO FIRE LOADER PISTON
    if(xBtn == true) {
      loader.set(Value.kForward);
    } else { 
      loader.set(Value.kReverse);
    }

    // READ YBUTTON TO FIRE FORTUNE PISTON
    if(yBtn == true) {
      fortune.set(Value.kForward);
    } else {
      fortune.set(Value.kReverse);
    }
/***********************************************************************************************************************************************/

    /* LIMELIGHT */
/***********************************************************************************************************************************************/
    // UPDATE NETWORKTABLES
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    // SET VARIABLES
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    double leftTrig = controller.getRawAxis(leftTrigger);
    
    if (leftTrig > .1) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      if (leftTrig < .7) {
        if (y < 0) {
          leftDrive.set(.5);
          rightDrive.set(-.5);
          if (y > -.1) {
            rightDrive.set(0);
            leftDrive.set(0);
          }
        }
        if (y > 0) {
          leftDrive.set(-.5);
          rightDrive.set(.5);
          if (y < .1) {
            rightDrive.set(0);
            leftDrive.set(0);
          }
        }
      } else {
        if (x < 0) {
          leftDrive.set(0);
          rightDrive.set(-.2);
          if (x > -.1) {
            rightDrive.set(0);
          }
        }
        if (x > 0) {
          rightDrive.set(0);
          leftDrive.set(.2);
          if (x < .1) {
            leftDrive.set(0);
          }
        }
      }
    } else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      leftDrive.set(-leftY);
      rightDrive.set(rightY);
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
