package com.w3spaces.electroboy.rpiI2CLCD;

import java.util.concurrent.TimeUnit;

import com.pi4j.wiringpi.I2C;

/**
 * 16x2 I2C LCD Driver for Java.
 * 
 * @author Electronics Boy
 * @version 0.0.2
 */
public class LCD {
	// File Discripter for I2C of pi4j v1.
	private static       int FILE_DISCRIPTER;
	
	// LCD data types
	private static final int LCD_CMD = 0x00;
	private static final int LCD_CHR = 0x01;
	
	// Backlight states
	private static final int LCD_BACKLIGHT_ON  = 0x08;
	private static final int LCD_BACKLIGHT_OFF = 0x00;
	private static int currentBacklightState   = LCD_BACKLIGHT_ON;
	
	// LCD control pins
	private static final int ENABLE = 0x04;
	
	// Is the LCD initialized?
	private static int init = 0;
	
	// Don't allow to create instances of the class
	private LCD() {}
	
	/**
	 * Function to initialize the lcd
	 *
	 * @param address address of the display.
	 * 
	 * @since 0.0.1
	 */
	public static void lcdInit(int address) {
		FILE_DISCRIPTER = I2C.wiringPiI2CSetup(address);
		if(FILE_DISCRIPTER < 0) { System.err.println("Error while initialization!"); System.exit(-1); }
		
		lcdByte(0x33, LCD_CMD);
		lcdByte(0x32, LCD_CMD);
		lcdByte(0x06, LCD_CMD);
		lcdByte(0x0C, LCD_CMD);
		lcdByte(0x28, LCD_CMD);
		lcdByte(0x01, LCD_CMD);
		EDELAY();
		
		init = 1;
	}
	/**
	 * Function to send text to the LCD
	 * 
	 * @param str Text to send
	 * 
	 * @since 0.0.1
	 */
	public static void printString(String str) {
		if(init != 1) throw new IllegalStateException("LCD is not initialized!");
		int i = 1;
		for(char c : str.toCharArray()) { if(i > 16) break; lcdByte(c, LCD_CHR); i++; }
	}
	/**
	 * Function to toggle the backlight on and off
	 * 
	 * @param state true for backlight on and false for backlight off
	 * 
	 * @since 0.0.1
	 */
	public static void setBacklight(boolean state) {
		currentBacklightState = state ? LCD_BACKLIGHT_ON : LCD_BACKLIGHT_OFF;
	}
	/**
	 * Function to clear the screen
	 * 
	 * @since 0.0.1
	 */
	public static void clear() {
		lcdByte(0x01, LCD_CMD);
		lcdByte(0x02, LCD_CMD);
	}
	
	/**
	 * Sets the cursor position
	 * 
	 * @param row Cursor row pos
	 * @param column Cursor col pos
	 * 
	 * @since 0.0.2
	 */
	public static void setCursor(int row, int column) {
		int[] rowOffsets = { 0x00, 0x40 };
		if(row > 1) row = 1;
		if(column > 15) column = 15;
		lcdByte(0x80 | (column + rowOffsets[row]), LCD_CMD);
	}
	
	// Function to send bytes to the lcd, Should not be public
	private static void lcdByte(int bytes, int mode) {
		int bitsHigh = mode | ( bytes       & 0xF0) | currentBacklightState;
		int bitsLow  = mode | ((bytes << 4) & 0xF0) | currentBacklightState;
		
		I2C.wiringPiI2CWrite(FILE_DISCRIPTER, bitsHigh);
		lcdPulseEN(bitsHigh);
		
		I2C.wiringPiI2CWrite(FILE_DISCRIPTER, bitsLow);
		lcdPulseEN(bitsLow);
	}
	// Function to toggle the EN pin of the LCD, Should not be public
	private static void lcdPulseEN(int bytes) {
		EDELAY();
		I2C.wiringPiI2CWrite(FILE_DISCRIPTER, (bytes |  ENABLE));
		EDELAY();
		I2C.wiringPiI2CWrite(FILE_DISCRIPTER, (bytes & ~ENABLE));
		EDELAY();
	}
	
	// A function to give a delay of 500,000 Nano Seconds. Should not be public
	private static final void EDELAY() {
		try {
			TimeUnit.NANOSECONDS.sleep(500000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
