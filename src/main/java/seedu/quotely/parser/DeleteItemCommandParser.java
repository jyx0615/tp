package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.DeleteItemCommand;
import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserConstant.DELETE_ITEM_COMMAND_PATTERN;

public class DeleteItemCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(DeleteItemCommandParser.class.getName());
    
    @Override
    public DeleteItemCommand parse(String arguments, QuotelyState state,
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseDeleteItemCommand called with arguments: " + arguments);
        Pattern p = Pattern.compile(DELETE_ITEM_COMMAND_PATTERN);
        Matcher m = p.matcher(arguments);
        if (m.find()) {
            String itemName = m.group(1).trim();
            String quoteName = m.group(2) != null ? m.group(2).trim() : null;
            Quote quote;
            try {
                quote = getQuoteFromStateAndName(quoteName, state, quoteList);
            } catch (QuotelyException e) {
                logger.warning("Failed to find quote name:" + quoteName + " for deleting item");
                if (quoteName != null) {
                    throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, quoteName);
                } else {
                    throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                            "delete i/ITEM_NAME [n/QUOTE_NAME]");
                }
            }
            if (!quote.hasItem(itemName)) {
                logger.warning("Item not found in quote - Item: '" + itemName +
                        "', Quote: '" + quote.getQuoteName() + "'");
                throw new QuotelyException(QuotelyException.ErrorType.ITEM_NOT_FOUND);
            }
            logger.info("Successfully parsed delete item command - Item: '" +
                    itemName + " for quote: '" + quote.getQuoteName() + "'");
            return new DeleteItemCommand(itemName, quote);
        } else {
            logger.warning("Invalid format for delete item command: " + arguments);
            throw new QuotelyException(
                    QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "delete i/ITEM_NAME [n/QUOTE_NAME]");
        }
    }

}
