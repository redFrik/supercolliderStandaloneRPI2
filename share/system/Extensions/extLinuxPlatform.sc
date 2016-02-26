+ LinuxPlatform {

	startupFiles {
		^[];
	}

	startup {

		helpDir = this.userAppSupportDir+/+"Help";

		// Server setup
		Server.program = "./bin/scsynth -U ./bin/plugins";

		// Score setup
		Score.program = Server.program;

		// default jack port hookup
		"SC_JACK_DEFAULT_INPUTS".setenv("system");
		"SC_JACK_DEFAULT_OUTPUTS".setenv("system");

		// automatically start jack when booting the server
		// can still be overridden with JACK_NO_START_SERVER
		"JACK_START_SERVER".setenv("true");

		// load user startup file
		//this.loadStartupFiles;
	}

	systemAppSupportDir {
		^"/home/pi/supercolliderStandaloneRPI2+/share/system";
	}
	userAppSupportDir {
		^"/home/pi/supercolliderStandaloneRPI2+/share/user";
	}
	systemExtensionDir {
		^this.systemAppSupportDir+/+"Extensions";
	}
	userExtensionDir {
		^this.userAppSupportDir+/+"Extensions";
	}
	userConfigDir {
		^this.userAppSupportDir;
	}
	resourceDir {
		^this.systemAppSupportDir;
	}
}
