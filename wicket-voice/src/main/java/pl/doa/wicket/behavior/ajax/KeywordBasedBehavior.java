package pl.doa.wicket.behavior.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.doa.wicket.behavior.AjaxVoiceBehavior;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * More complex version of {@link AjaxVoiceBehavior} when you can define a keyword-based interaction matrix. This
 * Behavior implementation is equipped with semi-fluent API easing developer's work (hopefuly ...).
 * <p/>
 * Each spoken keyword recognized by Google Speech Recognition engine triggers invocation of predefined action. Like
 * here:
 * <p/>
 * {@code
 * <p/>
 * add(new KeywordBasedBehavior() {
 *
 * protected void noKeywordRecognized(String text, AjaxRequestTarget target) { // when none of defined
 * keywords is recognized, but hey, there was something ... }
 * <p/>
 * protected void initKeywords(IKeywordManager manager) { manager.when("first", new IKeywordAction() { public void
 * performAction(AjaxRequestTarget target) throws Exception { // perform action when recognized text is "first" }
 * }).when("second", new IKeywordAction() { public void performAction(AjaxRequestTarget target) throws Exception { //
 * perform action when recognized text is "second" } }); }
 * <p/>
 * });
 * <p/>
 * }
 *
 * @author activey
 * @date 01.09.13 21:10
 */
public class KeywordBasedBehavior extends AjaxVoiceBehavior implements IKeywordManager {

    private final static Logger log = LoggerFactory.getLogger(KeywordBasedBehavior.class);
    private Map<String, IKeyword> keywords = Collections.synchronizedMap(new HashMap<String, IKeyword>());

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);

        // initializing keywords
        this.initKeywords(this);
    }

    @Override
    protected void onTextRecorded(String text, AjaxRequestTarget target) {
        IKeyword keyword = keywords.get(text);
        if (keyword == null) {
            this.noKeywordRecognized(text, target);
            return;
        }
        try {
            keyword.getAction().performAction(target);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Method is being invoked when none of predefined keyword is recognized. You can then prevent user from
     * unnecessary confusion when his command is ignored.
     *
     * @param text Recognized text not matched with any of defined keywords.
     * @param target Ajax response container.
     */
    protected void noKeywordRecognized(String text, AjaxRequestTarget target) {
    }

    /**
     * Override this method to define Behavior keywords.
     *
     * @param manager Reference to Keyword manager instance.
     */
    protected void initKeywords(IKeywordManager manager) {
    }

    @Override
    public IKeywordManager when(String keyword, IKeywordAction keywordAction) {
        keywords.put(keyword, new DefaultKeyword(keywordAction));
        return this;
    }

    private class DefaultKeyword implements IKeyword, Serializable {

        private final IKeywordAction keywordAction;

        public DefaultKeyword(IKeywordAction keywordAction) {
            this.keywordAction = keywordAction;
        }

        @Override
        public IKeywordAction getAction() {
            return this.keywordAction;
        }
    }

}
