package com.w3spaces.electroboy.raspberry_lcd;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class LCD {
	private GpioPinDigitalOutput rs, en, d4, d5, d6, d7;

	public LCD(int rs, int en, int d4, int d5, int d6, int d7) {
		GpioController gpio = GpioFactory.getInstance();
		this.rs = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(rs), "RS", PinState.HIGH);
		this.en = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(en), "EN", PinState.LOW);
		this.d4 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(d4), "D4", PinState.LOW);
		this.d5 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(d5), "D5", PinState.LOW);
		this.d6 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(d6), "D6", PinState.LOW);
		this.d7 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(d7), "D7", PinState.LOW);
	}
	
	public void init() {
		command(0x28, true);
		command(0x0C, true);
		command(0x01, true);
		
		command(0x80, true);
		command(0x33, false);
	}
//	public void setCursor(int row, int col) {
//		command(0x02, true);
//		command(row == 0 ? 0x08 : 0x0C, true);
//		for(int i = 0; i < col; i++) command(0x06, true);
//	}
//	public void cursorMode(CursorMode... modes) {
//		for(CursorMode mode : modes)
//			if(mode == CursorMode.CURSOR_OFF)
//				command(0x08, true);
//			else if(mode == CursorMode.CURSOR_ON)
//				command(0x0F, true);
//			else if(mode == CursorMode.CURSOR_BLINKING)
//				command(0x0E, true);
//	}
	
	private void command(int hex, boolean lcdcmd) {		
		try {
			rs.setState(!lcdcmd);
			
			d4.setState((hex & 0x10) == 0x10);	
			d5.setState((hex & 0x20) == 0x20);
			d6.setState((hex & 0x40) == 0x40);
			d7.setState((hex & 0x80) == 0x80);
			
			Thread.sleep(0, 500000);
			en.high();
			Thread.sleep(0, 500000);
			en.low();
			Thread.sleep(0, 500000);
			
			d4.setState((hex & 0x1) == 0x1);	
			d5.setState((hex & 0x2) == 0x2);
			d6.setState((hex & 0x4) == 0x4);
			d7.setState((hex & 0x8) == 0x8);
			
			Thread.sleep(0, 500000);
			en.high();
			Thread.sleep(0, 500000);
			en.low();
			Thread.sleep(0, 500000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
