package pl.doa.wicket.behavior.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * Interface defines basic methods that all keyword actions have to implement. Implementation instance is used in {@link
 * IKeywordManager}.
 *
 * @author activey
 * @date 01.09.13 21:18
 */
public interface IKeywordAction extends Serializable {

    /**
     * Method is invoked when related keyword has been recognized by {@link KeywordBasedBehavior}.
     *
     * @param target Ajax response container.
     * @throws Exception
     */
    public void performAction(AjaxRequestTarget target) throws Exception;
}
