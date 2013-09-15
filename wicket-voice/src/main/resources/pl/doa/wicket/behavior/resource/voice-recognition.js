var voiceRecognition = {};

(function($, voiceRecognition) {

	var VoiceRecognitionEngine = function() {
		this.busy = false;
		this.initialized = false;
		this.recognitionEngine = {};
		this.caller = {};

		this.initEngine = function() {
			if (typeof webkitSpeechRecognition != 'undefined') {
				this.recognitionEngine = new webkitSpeechRecognition();
				this.recognitionEngine.continuous = true;
				this.recognitionEngine.interimResults = true;

				var _that = this;
				this.recognitionEngine.onresult = function (event) {
					for (var i = event.resultIndex; i < event.results.length; ++i) {
						if (event.results[i].isFinal) {
							var text = event.results[i][0].transcript;
							var trimmed = $.trim(text);

							_that.onTextRecorded(trimmed);
						}
					}
				};

				this.recognitionEngine.onend = function() {
					_that.busy = false;
					_that.onRecordingFinished();
				};

				console.log('Speech Recognition Engine initialized');
				this.initialized = true;
			} else {
				console.warn('Speech Recognition Engine is not available!');
			}
		};

		this.onTextRecorded = function(text) {
        	if (this.caller.onTextRecorded) {
        		$.proxy(function() {
        			this.onTextRecorded(text);
        		}, this.caller).call(text);
        	}
		};

		this.onRecordingFinished = function() {
        	if (this.caller.onRecordingFinished) {
        		$.proxy(this.caller.onRecordingFinished, this.caller).call();
			}
        };

        this.start = function(caller) {
        	if (this.initialized === false) {
        		console.error('Speech Recognition Engine is not yet initialized!');
        	}
        	this.caller = caller;
        	this.recognitionEngine.start();
        	this.busy = true;
        };

        this.stop = function() {
			if (this.initialized === false) {
				console.error('Speech Recognition Engine is not yet initialized!');
			}
			this.recognitionEngine.stop();
			this.busy = false;
		};

		this.initEngine();
	};

    voiceRecognition.engine = new VoiceRecognitionEngine();

})(jQuery, voiceRecognition);