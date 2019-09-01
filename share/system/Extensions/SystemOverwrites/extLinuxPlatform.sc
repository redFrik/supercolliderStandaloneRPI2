+ LinuxPlatform {
	startupFiles {^super.startupFiles}
	startup {

		helpDir = this.systemAppSupportDir++"/Help";

		// Server setup
		Server.program = "exec ./scsynth";  //f0 edited
		Server.default.options.ugenPluginsPath = "plugins:share";  //f0 added
		"SC_SYNTHDEF_PATH".setenv(this.userAppSupportDir+/+"synthdefs");  //f0 added

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
	systemAppSupportDir {
		^File.getcwd+/+"share/system";
	}
	userAppSupportDir {
		^File.getcwd+/+"share/user";
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
