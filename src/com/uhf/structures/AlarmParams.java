package com.uhf.structures;

public class AlarmParams {
	public int mode = 0;
	public int alarmTimes = 0;
	public int ignoreSec = 0;
	public int alarmSec = 0;
	public int matchStart = 0;
	public int matchLen = 0;
	public byte[] match = new byte[64];

	public AlarmParams() {

	}

	public AlarmParams(int mode, int alarmTimes, int ignoreSec, int alarmSec,
			int matchStart, int matchLen, byte[] match) {
		setValue(mode, alarmTimes, ignoreSec, alarmSec, matchStart, matchLen,
				match);
	}

	public void setValue(int mode, int alarmTimes, int ignoreSec, int alarmSec,
			int matchStart, int matchLen, byte[] match) {
		this.mode = mode;
		this.alarmTimes = alarmTimes;
		this.ignoreSec = ignoreSec;
		this.alarmSec = alarmSec;
		this.matchStart = matchStart;
		this.matchLen = matchLen;
		this.match = match;
	}
}
