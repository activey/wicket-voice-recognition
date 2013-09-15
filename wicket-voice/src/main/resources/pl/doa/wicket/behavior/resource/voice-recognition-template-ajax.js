if (voiceRecognition.applicationController) {
	// TODO !
	return;
}
voiceRecognition.applicationController = new voiceRecognition.ApplicationController("#${callbackUrl}");
voiceRecognition.applicationController.initController();