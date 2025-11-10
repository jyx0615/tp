package seedu.quotely.parser;

import seedu.quotely.command.FinishQuoteCommand;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;

public class FinishQuoteCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FinishQuoteCommandParser.class.getName());
    
    public FinishQuoteCommand parse(String arguments, QuotelyState state, QuoteList quoteList)
            throws QuotelyException {
        logger.fine("parseFinishQuoteCommand called");
        if (state.isInsideQuote()) {
            logger.info("Successfully parsed finish quote command");
            return new FinishQuoteCommand();
        } else {
            logger.warning("Attempted to finish quote while not inside a quote");
            throw new QuotelyException(QuotelyException.ErrorType.NO_ACTIVE_QUOTE);
        }
    }

    
}
