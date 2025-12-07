package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.DeleteQuoteCommand;
import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserConstant.QUOTENAME_ARG_PATTERN;

public class DeleteQuoteCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(DeleteQuoteCommandParser.class.getName());
            
    @Override
    public DeleteQuoteCommand parse(String arguments, QuotelyState state,
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseDeleteQuoteCommand called with arguments: " + arguments);
        Pattern p = Pattern.compile(QUOTENAME_ARG_PATTERN);
        Matcher m = p.matcher(arguments);

        String quoteName = null;
        if (m.find()) {
            quoteName = m.group(1).trim();
        } else if (arguments.length() > 0) {
            // if arguments were provided but did not match pattern
            logger.warning("Invalid format for delete quote command: " + arguments);
            throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT, "unquote [n/QUOTE_NAME]");
        }
        try {
            Quote quote = getQuoteFromStateAndName(quoteName, state, quoteList);
            logger.info("Successfully parsed delete quote command for quote: " + quote.getQuoteName());
            return new DeleteQuoteCommand(quote);
        } catch (QuotelyException e) {
            logger.warning("Failed to find quote for deletion with name: " + quoteName);
            if (quoteName != null) {
                throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, quoteName);
            } else {
                throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT, "unquote [n/QUOTE_NAME]");
            }
        }
    }
}
