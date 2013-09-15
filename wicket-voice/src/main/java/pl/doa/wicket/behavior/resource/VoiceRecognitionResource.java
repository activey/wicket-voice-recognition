package pl.doa.wicket.behavior.resource;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;

import java.util.ArrayList;

/**
 * Wicket resource that defines reference to voice-recognition.js file.
 *
 * @author activey
 * @date 31.08.13 15:40
 */
public class VoiceRecognitionResource extends JavaScriptResourceReference {

    private static final VoiceRecognitionResource INSTANCE = new VoiceRecognitionResource();

    private VoiceRecognitionResource() {
        super(VoiceRecognitionResource.class, "voice-recognition.js");
    }

    public static VoiceRecognitionResource get() {
        return INSTANCE;
    }

    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        return new ArrayList<HeaderItem>() {{
            add(JavaScriptReferenceHeaderItem
                    .forReference(JQueryResourceReference.get()));
        }};
    }

}
