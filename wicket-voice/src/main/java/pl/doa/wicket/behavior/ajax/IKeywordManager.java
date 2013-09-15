package pl.doa.wicket.behavior.ajax;

/**
 * Interface provides some basic methods for managing keyword-based matrix for {@link KeywordBasedBehavior}.
 *
 * @author activey
 * @date 01.09.13 21:17
 */
public interface IKeywordManager {

    /**
     * By calling this method you can create new keyword matching entry, like this:
     *
     * {@code
     * manager.when("first", new IKeywordAction() {
     *      public void performAction(AjaxRequestTarget target) throws Exception {
     *          // perform some action
     *      }
     * })
     *
     * }
     *
     * @param keyword Keyword text that will trigger keyword action.
     * @param keywordAction Keyword action implementation.
     * @return Back reference to {@link IKeywordManager}.
     */
    public IKeywordManager when(String keyword, IKeywordAction keywordAction);
}
