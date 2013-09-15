Wicket Voice Recognition
========================

This library consists of three custom Apache Wicket Behavior implementations that enables voice control over your
Web application. All of them are strongly coupled with WebKit's webkitSpeechRecognition class that is built into Web browsers
like Chromium, Google Chrome, etc.

Library code consists both of JavaScript code and Java code that wraps it all and makes reaching recognized
text from within Wicket code possible.


AjaxVoiceBehavior
-----------------
This is the basic behavior used for Wicket Ajax interaction. Let's have a look at simple usage:

```java
  public class TestPage extends Page {
 
      public TestPage() {
          super();
      }
 
      public void onInitialize() {
          super.onInitialize();
 
          add(new AjaxVoiceBehavior() {
              protected void onTextRecorded(String text, AjaxRequestTarget target) {
                  // perform some action
              }
          });
      }
  }

```
Fairy simple, isn't it? ;)


TextFieldDictationBehavior
--------------------------

Behavior implements Voice Recognition JavaScript callbacks around given component, whereas Behavior is atteched to.
It's just a complex wrapped around some of html components:


* ```<input type="text" />```
* ```<textarea />```

To use it, just do simply:

```java
TextField field1 = new TextField("field1");
field1.add(new TextFieldDictationBehavior());
add(field1);
```

That will produce default predefined html code wrapper around your html controls using default assets like
microphone icon, animation icon, disabled styles, etc. You can customize default behavior by overriding some of
protected methods.


KeywordBasedBehavior
--------------------
More complex version of {@link AjaxVoiceBehavior} when you can define a keyword-based interaction matrix. This
Behavior implementation is equipped with semi-fluent API easing developer's work (hopefuly ...).

Each spoken keyword recognized by Google Speech Recognition engine triggers invocation of predefined action. Like
here:

```java
add(new KeywordBasedBehavior() {

	@Override protected void noKeywordRecognized(String text, AjaxRequestTarget target) { 
		// when none of defined	keywords is recognized, but hey, there was something ... 
	}

	protected void initKeywords(IKeywordManager manager) {
		manager.when("first", new IKeywordAction() {
			public void performAction(AjaxRequestTarget target) throws Exception {
				// perform action when recognized text is "first"
			}
			}).when("second", new IKeywordAction() {
				public void performAction(AjaxRequestTarget target) throws Exception {
				//perform action when recognized text is "second" }
			});
	}

});
```
