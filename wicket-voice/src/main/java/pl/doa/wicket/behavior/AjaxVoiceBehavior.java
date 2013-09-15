package pl.doa.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WicketAjaxJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import pl.doa.wicket.behavior.resource.VoiceRecognitionResource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic behavior used for Wicket Ajax interaction. Let's have a look at simple usage:
 *
 * {@code
 *
 * public class TestPage extends Page {
 *
 *     public TestPage() {
 *         super();
 *     }
 *
 *     public void onInitialize() {
 *         super.onInitialize();
 *
 *         add(new AjaxVoiceBehavior() {
 *             protected void onTextRecorded(String text, AjaxRequestTarget target) {
 *                 // perform some action
 *             }
 *         });
 *     }
 * }
 *
 * }
 *
 * As you can see implementation is fair simple and requires to be one method implemented only (@see AjaxVoiceBehavior#onTextRecorded).
 *
 * @author activey
 * @date 23.08.13 15:33
 */
public class AjaxVoiceBehavior extends AbstractDefaultAjaxBehavior {

    @Override
    public void onConfigure(Component component) {
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem
                .forReference(new JavaScriptResourceReference(
                        AjaxVoiceBehavior.class, "resource/voice-recognition-ajax.js") {
                    @Override
                    public Iterable<? extends HeaderItem> getDependencies() {
                        return new ArrayList<HeaderItem>() {{
                            add(
                                    JavaScriptReferenceHeaderItem
                                            .forReference(WicketAjaxJQueryResourceReference.get()));
                            add(
                                    JavaScriptReferenceHeaderItem
                                            .forReference(VoiceRecognitionResource.get()));

                        }};
                    }
                }));

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("callbackUrl", this.getCallbackUrl());
        response.render(OnDomReadyHeaderItem.forScript(new PackageTextTemplate(
                AjaxVoiceBehavior.class, "resource/voice-recognition-template-ajax.js")
                .asString(variables)));
    }

    /**
     * Method is beeing invoked immediately after some recorded voice has been recognized.
     *
     * @param text Recognized text from recorded audio.
     * @param target Ajax container.
     */
    protected void onTextRecorded(String text, AjaxRequestTarget target) {

    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        // getting http request reference
        RequestCycle requestCycle = RequestCycle.get();
        ServletWebRequest webRequest = (ServletWebRequest) requestCycle
                .getRequest();
        HttpServletRequest request = webRequest.getContainerRequest();

        String text = request.getParameter("text");
        onTextRecorded(text, target);
    }
}
