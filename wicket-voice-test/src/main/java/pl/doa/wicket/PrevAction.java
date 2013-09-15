package pl.doa.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import pl.doa.wicket.behavior.ajax.IKeywordAction;

/**
 * @author activey
 * @date: 04.09.13 19:57
 */
public class PrevAction implements IKeywordAction {
    @Override
    public void performAction(AjaxRequestTarget target) throws Exception {
        target.appendJavaScript("Foundation.libs.orbit.previous($('#gallery'));");
    }
}
