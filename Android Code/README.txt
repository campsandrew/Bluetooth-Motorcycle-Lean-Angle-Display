Description: Bluetooth gyroscopic lean angle for motorcycle. Phone connects to arduino bluetooth device and transmit lean angle data from the gyroscope on the phone. The arduino will then display this data on an led matrix display in real time.

Actions: 

- Connect to device on app startup (Automatically if around)
- If first time startup
	Read in button input from arduino for lean configuration
	(phone in pocket, mounted on bike, where angle zero is)
- else
	remember last lean configuration

- Button for starting or stop recording on phone from arduino

- Unrecorded/Recorded lean angles throughout ride while displaying realtime lean angle on led matrix display

- Lean angle playback feature on phone display, or button to select play back of most recent recording of leans/farthest lean angle

