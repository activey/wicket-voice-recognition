package pl.doa.wicket;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import pl.doa.wicket.behavior.AjaxVoiceBehavior;
import pl.doa.wicket.behavior.TextFieldDictationBehavior;

public class TestFormPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public TestFormPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new TextField<String>("basic_textfield").add(new TextFieldDictationBehavior("#field_mic") {

            public String getMicPlaceholder() {
                return "<i class='icon-microphone'></i>";
            }

            public String getDisabledClass() {
                return "disabled";
            }

            public String getActiveClass() {
                return "animation-active";
            }
        }));

        add(new TextArea<String>("textarea").add(new TextFieldDictationBehavior("#textarea_mic") {

            public String getMicPlaceholder() {
                return "<i class='icon-microphone'></i>";
            }

            public String getDisabledClass() {
                return "disabled";
            }

            public String getActiveClass() {
                return "animation-active";
            }
        }));
    }
}
