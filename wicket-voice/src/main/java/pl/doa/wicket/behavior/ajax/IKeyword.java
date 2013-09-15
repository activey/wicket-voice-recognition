package pl.doa.wicket.behavior.ajax;

/**
 * Helper interface for {@link KeywordBasedBehavior}.
 *
 * @author activey
 * @date 01.09.13 21:16
 */
public interface IKeyword {

    /**
     * Returns keyword action implementation.
     * @return
     */
    public IKeywordAction getAction();
}
