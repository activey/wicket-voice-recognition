package pl.doa.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import pl.doa.wicket.behavior.resource.VoiceRecognitionResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Behavior implements Voice Recognition JavaScript callbacks around given component, whereas Behavior is atteched to.
 * It's just a complex wrapped around some of html components:
 *
 * <ul>
 *     <li>
 *         <input type="text" />
 *     </li>
 *     <li>
 *         <textarea />
 *     </li>
 * </ul>
 *
 * To use it, just do simply:
 *
 * {@code
 * TextField field1 = new TextField("field1");
 * field1.add(new TextFieldDictationBehavior());
 * add(field1);
 * }
 *
 * That will produce default predefined html code wrapper around your html controls using default assets like
 * microphone icon, animation icon, disabled styles, etc. You can customize default behavior by overriding some of
 * protected methods.
 *
 * @author activey
 * @date 23.08.13 15:33
 */
public class TextFieldDictationBehavior extends Behavior {

    private final String micContainer;

    /**
     * Default behavior constructor
     */
    public TextFieldDictationBehavior() {
        this.micContainer = "$input.parent();";
    }

    /**
     * Constructor allows to change default behavior in terms of locating container element where microphone icon should
     * be appended into. Use jquery selector style like this:
     *
     * {@code
     *  add(new TextField<String>("field1").add(new TextFieldDictationBehavior("#mic_container")));
     * }
     *
     * @param micContainerSelector
     */
    public TextFieldDictationBehavior(String micContainerSelector) {
        this.micContainer = "$('" + micContainerSelector + "');";
    }

    @Override
    public void onConfigure(Component component) {
        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(CssReferenceHeaderItem
                .forReference(new CssResourceReference(
                        AjaxVoiceBehavior.class, "resource/voice-recognition.css")));
        response.render(JavaScriptReferenceHeaderItem
                .forReference(new JavaScriptResourceReference(
                        AjaxVoiceBehavior.class, "resource/voice-recognition-dictation.js") {
                    @Override
                    public Iterable<? extends HeaderItem> getDependencies() {
                        return new ArrayList<HeaderItem>() {{
                            add(
                                    JavaScriptReferenceHeaderItem
                                            .forReference(VoiceRecognitionResource.get()));
                            add(JavaScriptReferenceHeaderItem
                                    .forReference(new JavaScriptResourceReference(
                                            AjaxVoiceBehavior.class, "resource/jquery.at.caret.min.js")));
                        }};
                    }
                }));

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("componentId", component.getMarkupId());
        variables.put("micContainer", this.micContainer);
        variables.put("micPlaceholder", getMicPlaceholder());
        variables.put("disabledClass", getDisabledClass());
        variables.put("activeClass", getActiveClass());
        response.render(OnDomReadyHeaderItem.forScript(new PackageTextTemplate(
                AjaxVoiceBehavior.class, "resource/voice-recognition-template.js")
                .asString(variables)));
    }

    /**
     * Method is used to generate html fragment that will hold microphone icon placeholder. Override it to provide your
     * own code.
     *
     * @return Html fragment.
     */
    protected String getMicPlaceholder() {
        return "<div class='speech-content-mic speech-mic'/>";
    }

    /**
     * Method returns Css class value used to decorate microphone placeholder element when device is busy.
     *
     * @return Css class value.
     */
    protected String getDisabledClass() {
        return "speech-mic-disabled";
    }

    /**
     * Method returns Css class value used to decorate microphone placeholder element when device is active.
     *
     * @return Css class value.
     */
    protected String getActiveClass() {
        return "speech-mic-works";
    }

}
