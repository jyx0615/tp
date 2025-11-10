package seedu.quotely.parser;

import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;

public class ParserUtil {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(ParserUtil.class.getName());

    public enum CommandContext {
        MAIN,   // command allowed only in main (not inside a quote)
        QUOTE,  // command allowed only when inside a quote
        BOTH    // command allowed in both contexts
    }

    // Method for validating names
    public static boolean isValidName(String s) {
        // check if string only contains expected char types
        return s != null && s.matches("[A-Za-z0-9 _'&.,()\\-]+");
    }

    public static Quote getQuoteFromStateAndName(String quoteName,
                                                 QuotelyState state, QuoteList quoteList) throws QuotelyException {
        // Precondition assertions
        assert state != null : "QuotelyState cannot be null";
        assert quoteList != null : "QuoteList cannot be null";

        logger.fine("getQuoteFromStateAndName called");

        if (quoteName == null && state.getQuoteReference() == null) {
            logger.warning("No quote name provided and no active quote in state");
            throw new QuotelyException(QuotelyException.ErrorType.NO_ACTIVE_QUOTE);
        } else if (quoteName != null) {
            logger.fine("Looking up quote by name: " + quoteName);
            return quoteList.getQuoteByName(quoteName);
        } else {
            logger.fine("Using current quote from state");
            return state.getQuoteReference();
        }
    }

    /**
     * Check whether the current application state satisfies the required command context.
     * - MAIN: command may only be used when not inside a quote
     * - QUOTE: command may only be used when inside a quote
     * - BOTH: command may be used in both contexts
     *
     * @param state    current QuotelyState (must not be null)
     * @param required required CommandContext for the command
     * @return true if the command is allowed in the current state, false otherwise
     */
    public static boolean checkState(QuotelyState state, CommandContext required) {
        // Precondition assertions
        assert state != null : "QuotelyState cannot be null";
        if (required == null) {
            // treat null requirement as BOTH (safer fallback)
            return true;
        }

        boolean insideQuote = state.isInsideQuote();
        switch (required) {
        case BOTH:
            return true;
        case QUOTE:
            return insideQuote;
        case MAIN:
            return !insideQuote;
        default:
            return false;
        }
    }
}
