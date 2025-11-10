package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.SearchQuoteCommand;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserConstant.QUOTENAME_ARG_PATTERN;

public class SearchCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SearchCommandParser.class.getName());

    public SearchQuoteCommand parse(String arguments, QuotelyState state,
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseSearchCommand called");
        String quoteName;

        Pattern p = Pattern.compile(QUOTENAME_ARG_PATTERN);
        Matcher m = p.matcher(arguments);

        if (m.find()) {
            quoteName = m.group(1).trim();
            logger.info("Successfully parsed search quote command");
            return new SearchQuoteCommand(quoteName);
        } else {
            logger.warning("Invalid format for search item command: " + arguments);
            throw new QuotelyException(
                    QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "search n/QUOTE_NAME");
        }
    }

}
