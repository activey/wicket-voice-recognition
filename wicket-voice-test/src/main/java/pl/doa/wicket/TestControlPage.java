package pl.doa.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import pl.doa.wicket.behavior.ajax.IKeywordManager;
import pl.doa.wicket.behavior.ajax.KeywordBasedBehavior;

public class TestControlPage extends WebPage {
    private static final long serialVersionUID = 1L;

    public TestControlPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new KeywordBasedBehavior() {
            @Override
            protected void onTextRecorded(String text, AjaxRequestTarget target) {
                target.appendJavaScript(String
                        .format("noty({text: '<b>Recognized text:</b> %s', timeout: 2000});", text));

                // remember to call super method
                super.onTextRecorded(text, target);
            }

            @Override
            protected void noKeywordRecognized(String text, AjaxRequestTarget target) {

            }

            @Override
            protected void initKeywords(IKeywordManager manager) {
                manager.when("dalej", new NextAction()).
                        when("next", new NextAction()).
                        when("powrót", new PrevAction()).
                        when("back", new PrevAction());
            }
        });
    }
}
