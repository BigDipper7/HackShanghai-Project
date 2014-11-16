package com.eva.me.hackshanghai_diffchoice.util;


public class MathUtils {
	public static int getRealID(int input, int maxVal) {
		if (input < 0) {
			input += (-input) / maxVal *maxVal + maxVal;
			
		}
		input %= maxVal;
		return input;
	}
	
}
