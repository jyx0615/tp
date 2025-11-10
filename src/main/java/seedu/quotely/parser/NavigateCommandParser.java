package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.NavigateCommand;
import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserConstant.QUOTENAME_ARG_PATTERN;

public class NavigateCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(NavigateCommandParser.class.getName());

    public NavigateCommand parse(String arguments, QuotelyState state, QuoteList quoteList)
            throws QuotelyException {
        logger.fine("parseNavigateCommand called with arguments: " + arguments);

        String targetName = arguments.trim();
        if (targetName.equalsIgnoreCase("main")) {
            return new NavigateCommand();
        }

        Pattern p = Pattern.compile(QUOTENAME_ARG_PATTERN);
        Matcher m = p.matcher(arguments);

        String targetQuoteName = null;
        if (m.find()) {
            targetQuoteName = m.group(1).trim();
        } else {
            logger.warning("Failed to navigate to target with name: " + targetQuoteName);
            throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT, "nav main OR nav n/QUOTE_NAME");
        }

        try {
            Quote targetQuote = getQuoteFromStateAndName(targetQuoteName, state, quoteList);
            logger.info("Successfully parsed navigate command to target location" + targetQuote.getQuoteName());
            return new NavigateCommand(targetQuote);
        } catch (QuotelyException e) {
            logger.warning("Failed to navigate to target with name: " + targetQuoteName);
            if (targetQuoteName != null) {
                throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, targetQuoteName);
            } else {
                throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                        "nav main OR nav n/QUOTE_NAME");
            }
        }
    }

}
