package com.w3spaces.electroboy.raspberry_lcd.test;

import com.w3spaces.electroboy.raspberry_lcd.CursorMode;
import com.w3spaces.electroboy.raspberry_lcd.LCD;

public class Test {
	public static void main(String[] args) {
		LCD lcd = new LCD(28, 29, 10, 11, 31, 26);
		lcd.init();
//		lcd.cursorMode(CursorMode.CURSOR_ON);
//		lcd.cursorMode(CursorMode.CURSOR_BLINKING);
//		lcd.setCursor(0, 0);
	}
}
