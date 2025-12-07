package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.command.ExportQuoteCommand;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserConstant.EXPORT_QUOTENAME_ARG_PATTERN;
import static seedu.quotely.parser.ParserConstant.FILENAME_ARG_PATTERN;
import static seedu.quotely.parser.ParserConstant.EXPORT_START_PATTERN;


public class ExportQuoteCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(ExportQuoteCommandParser.class.getName());

    @Override
    public ExportQuoteCommand parse(String arguments, QuotelyState state,
                                              QuoteList quoteList) throws QuotelyException {
        logger.fine("parseExportCommand called with arguments: " + arguments);
        Pattern p = Pattern.compile(EXPORT_QUOTENAME_ARG_PATTERN);
        Matcher m = p.matcher(arguments);
        Pattern f = Pattern.compile(FILENAME_ARG_PATTERN);
        Matcher fileMatcher = f.matcher(arguments);
        Pattern startPattern = Pattern.compile(EXPORT_START_PATTERN);
        Matcher startMatcher = startPattern.matcher(arguments);
        if (arguments.length() > 0 && !startMatcher.find()) {
            logger.warning("Invalid format for export quote command: " + arguments);
            throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "export [n/QUOTE_NAME] [f/FILENAME]");
        }

        String quoteName = null;
        if (m.find()) {
            quoteName = m.group(1).trim();
        }

        try {
            Quote quote = getQuoteFromStateAndName(quoteName, state, quoteList);
            String filename = quote.getQuoteName();
            if (fileMatcher.find() && fileMatcher.group(1).trim().length() > 0) {
                filename = fileMatcher.group(1).trim();
            }
            logger.info("Successfully parsed export quote command for quote: " + quote.getQuoteName());
            return new seedu.quotely.command.ExportQuoteCommand(quote, filename);
        } catch (QuotelyException e) {
            logger.warning("Failed to find quote for export with name: " + quoteName);
            if (quoteName != null) {
                throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, quoteName);
            } else {
                throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                        "export [n/QUOTE_NAME] [f/FILENAME]");
            }
        }
    }
}
