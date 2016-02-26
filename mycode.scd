Server.program= "exec ./scsynth";
s.options.ugenPluginsPath= "plugins";
s.options.loadDefs= false;
//"SC_JACK_DEFAULT_INPUTS".setenv("system");
//"SC_JACK_DEFAULT_OUTPUTS".setenv("system");
s.waitForBoot{
	{SinOsc.ar([400, 404], 0, 0.5)}.play;
};
