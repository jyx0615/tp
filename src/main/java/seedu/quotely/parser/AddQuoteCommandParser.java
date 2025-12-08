package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.AddQuoteCommand;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.isValidName;
import static seedu.quotely.parser.ParserConstant.MAX_CUSTOMERNAME_LENGTH;
import static seedu.quotely.parser.ParserConstant.MAX_QUOTENAME_LENGTH;
import static seedu.quotely.parser.ParserConstant.ADD_QUOTE_COMMAND_PATTERN;

public class AddQuoteCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AddQuoteCommandParser.class.getName());

    @Override
    public AddQuoteCommand parse(String arguments, QuotelyState state, QuoteList quoteList) throws QuotelyException {
        logger.fine("parseAddQuoteCommand called with arguments: " + arguments);

        Pattern p = Pattern.compile(ADD_QUOTE_COMMAND_PATTERN);
        Matcher m = p.matcher(arguments);

        if (m.find()) {
            String quoteName = m.group(1).trim();
            String customerName = m.group(2).trim();

            validateQuoteName(quoteName);
            validateCustomerName(customerName);

            logger.info("Successfully parsed add quote command - Quote: '"
                    + quoteName + "', Customer: '" + customerName + "'");
            return new AddQuoteCommand(quoteName, customerName);
        } else {
            logger.warning("Invalid format for add quote command: " + arguments);
            throw new QuotelyException(
                    QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "quote n/QUOTE_NAME c/CUSTOMER_NAME");
        }
    }

    private void validateQuoteName(String quoteName) throws QuotelyException {
        if (quoteName.length() > MAX_QUOTENAME_LENGTH || !isValidName(quoteName)) {
            logger.warning("Invalid quote name for add quote command: " + quoteName);
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_QUOTE_NAME);
        }
    }

    private void validateCustomerName(String customerName) throws QuotelyException {
        if (customerName.length() > MAX_CUSTOMERNAME_LENGTH || !isValidName(customerName)) {
            logger.warning("Invalid customer name for add quote command: " + customerName);
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_CUSTOMER_NAME);
        }
    }
}
