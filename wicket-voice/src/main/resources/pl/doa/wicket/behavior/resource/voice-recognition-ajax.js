(function($, voiceRecognition) {

	var ApplicationController = function(callbackUrl) {
		this.recording = false;
		this.callbackUrl = callbackUrl.replace(/^.*#/, '');

		this.initController = function() {
			if (voiceRecognition.engine.initialized === false) {
				console.warn('Recorder can not be initialised!');
				return;
			}
			this.startRecording();
		};

		this.onTextRecorded = function(text) {
        	Wicket.Ajax.get({'u': this.callbackUrl, 'ep':{'text': text}});
		};

		this.onRecordingFinished = function() {
			this.recording = false;
        	this.startRecording();
		};

		this.startRecording = function() {
			if (this.recording === true || voiceRecognition.engine.busy === true) {
				return;
			}
			voiceRecognition.engine.start(this);
			this.recording = true;
		};

		this.stopRecording = function() {
            voiceRecognition.engine.stop();
            this.recording = false;
		};
	};

	voiceRecognition.ApplicationController = ApplicationController;


})(jQuery, voiceRecognition);