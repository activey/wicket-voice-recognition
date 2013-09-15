voiceRecognition.recorder_${componentId} = new voiceRecognition.TextFieldRecorder("#${componentId}");
voiceRecognition.recorder_${componentId}.getMicContainer = function($input) {
	return ${micContainer};
};
voiceRecognition.recorder_${componentId}.micPlaceholder = "${micPlaceholder}";
voiceRecognition.recorder_${componentId}.disabledClass = "${disabledClass}";
voiceRecognition.recorder_${componentId}.activeClass = "${activeClass}";
voiceRecognition.recorder_${componentId}.initRecorder();