(function($, voiceRecognition) {

	var TextFieldRecorder = function(inputSelector) {
		this.recording = false;
		this.inputSelector = inputSelector;
		this.micPlaceholder = '<div class="speech-content-mic speech-mic"/>';
		this.disabledClass = 'speech-mic-disabled';
		this.activeClass = 'speech-mic-works';


		this.initRecorder = function() {
			if (voiceRecognition.engine.initialized === false) {
				console.warn('Recorder can not be initialised!');
				return;
			}

			var $input = $(this.inputSelector);
			var $micContainer = this.getMicContainer($input);
			$mic = $(this.micPlaceholder);
			$mic.addClass('speech-content-mic');

			$micContainer.append($mic);

			var $controller = $mic.closest('a');
			if ($controller.length === 0) {
				$controller = $mic;
			}

			$controller.click($.proxy(function() {
				if (this.recording === true) {
					this.stopRecording();
					return;
				}

				this.startRecording();
			}, this));
		};

    	this.getMicContainer = function($input) {
    		return $input.parent();
    	};

		/**
		 * Function is beeing invoked when text in recording engine has been recorded.
		 */
		this.onTextRecorded = function(text) {
			var $input = $(this.inputSelector);
			$input.atCaret('insert',text);
		};

		this.onRecordingFinished = function() {
			var $allMics = $('.speech-content-mic');
			$allMics.removeClass(this.disabledClass);

			var $input = $(this.inputSelector);
			var $micContainer = this.getMicContainer($input);
			$mic = $('.speech-content-mic', $micContainer);
			$mic.removeClass(this.activeClass);
		};

		this.startRecording = function() {
			if (this.recording === true || voiceRecognition.engine.busy === true) {
				return;
			}

			voiceRecognition.engine.start(this);
			var $allMics = $('.speech-content-mic');
			$allMics.addClass(this.disabledClass);

			var $input = $(this.inputSelector);
			var $micContainer = this.getMicContainer($input);
			$mic = $('.speech-content-mic', $micContainer);
			if ($mic === null) {
				return;
			}

			$mic.removeClass(this.disabledClass).addClass(this.activeClass);
			$input.focus();
			this.recording = true;
		};

		this.stopRecording = function() {
            voiceRecognition.engine.stop();
            this.recording = false;
		};
	};

	voiceRecognition.TextFieldRecorder = TextFieldRecorder;


})(jQuery, voiceRecognition);