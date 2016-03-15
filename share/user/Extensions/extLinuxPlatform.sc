+ LinuxPlatform {
	startup {

		helpDir = this.systemAppSupportDir++"/Help";

		// Server setup
		Server.program = "exec ./scsynth";  //f0 edited
		Server.default.options.ugenPluginsPath = "plugins";  //f0 added

		// Score setup
		Score.program = Server.program;

		// default jack port hookup
		"SC_JACK_DEFAULT_INPUTS".setenv("system");
		"SC_JACK_DEFAULT_OUTPUTS".setenv("system");

		// automatically start jack when booting the server
		// can still be overridden with JACK_NO_START_SERVER
		"JACK_START_SERVER".setenv("true");

		// load user startup file
		this.loadStartupFiles;
	}
}
