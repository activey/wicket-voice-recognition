package pl.doa.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import pl.doa.wicket.behavior.ajax.IKeywordAction;

/**
 * @author activey
 * @date: 04.09.13 19:56
 */
public class NextAction implements IKeywordAction {
    @Override
    public void performAction(AjaxRequestTarget target) throws Exception {
        target.appendJavaScript("Foundation.libs.orbit.next($('#gallery'));");
    }
}
