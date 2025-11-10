package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.CalculateTotalCommand;
import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserConstant.QUOTENAME_ARG_PATTERN;

public class TotalCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(TotalCommandParser.class.getName());
    
    public CalculateTotalCommand parse(String arguments, QuotelyState state,  
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseCalculateTotalCommand called with arguments: " + arguments);
        Pattern p = Pattern.compile(QUOTENAME_ARG_PATTERN);
        Matcher m = p.matcher(arguments);
        String quoteName = null;
        if (m.find()) {
            quoteName = m.group(1).trim();
        }
        try {
            Quote quote = getQuoteFromStateAndName(quoteName, state, quoteList);
            return new CalculateTotalCommand(quote);
        } catch (QuotelyException e) {
            logger.warning("Invalid format for calculate total command: " + arguments);
            if (quoteName != null) {
                throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, quoteName);
            } else {
                throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                        "total [n/QUOTE_NAME]");
            }
        }
    }

}
