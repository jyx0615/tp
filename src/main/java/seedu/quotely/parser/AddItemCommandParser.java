package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Parser;

import seedu.quotely.command.AddItemCommand;
import seedu.quotely.data.Quote;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;

import static seedu.quotely.parser.ParserUtil.getQuoteFromStateAndName;
import static seedu.quotely.parser.ParserUtil.isValidName;
import static seedu.quotely.parser.ParserConstant.MAX_ITEMNAME_LENGTH;
import static seedu.quotely.parser.ParserConstant.MAX_ITEMS;
import static seedu.quotely.parser.ParserConstant.MAX_PRICE;
import static seedu.quotely.parser.ParserConstant.MAX_QTY;
import static seedu.quotely.parser.ParserConstant.MAX_TAX_RATE;
import static seedu.quotely.parser.ParserConstant.ADD_ITEM_COMMAND_PATTERN;

public class AddItemCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AddItemCommandParser.class.getName());
    
    @Override
    public AddItemCommand parse(String arguments, QuotelyState state, 
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseAddItemCommand called with arguments: " + arguments);
        Pattern p = Pattern.compile(ADD_ITEM_COMMAND_PATTERN);
        Matcher m = p.matcher(arguments);

        if (m.find()) {
            String itemName = m.group(1).trim();
            validateItemName(itemName);
            String quoteName = m.group(2) != null ? m.group(2).trim() : null;
            String priceStr = m.group(3).trim();
            String quantityStr = m.group(4).trim();
            String taxRateStr = m.group(5) != null ? m.group(5).trim() : null;

            logger.fine("Extracted - Item: '" + itemName + "', Quote: '" +
                    (quoteName != null ? quoteName : "<none>") + "', Price: '" +
                    priceStr + "', Quantity: '" + quantityStr + "'" + "', Tax: '" +
                    (taxRateStr != null ? taxRateStr : "<none>"));

            Quote quote;

            // extract quote from quoteName or state
            try {
                quote = getQuoteFromStateAndName(quoteName, state, quoteList);
            } catch (QuotelyException e) {
                logger.warning("Failed to find quote name:" + quoteName + " for adding item");
                if (quoteName != null) {
                    throw new QuotelyException(QuotelyException.ErrorType.QUOTE_NOT_FOUND, quoteName);
                } else {
                    throw new QuotelyException(QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                            "add i/ITEM_NAME [n/QUOTE_NAME] p/PRICE q/QUANTITY [t/TAX_RATE]");
                }
            }

            checkCapacity(quote);
            double price = parsePrice(priceStr);
            int quantity = parseQuantity(quantityStr);
            double taxRate = parseTaxRate(taxRateStr);

            logger.info("Successfully parsed add item command - Item: '" +
                    itemName + "' Price: " + price + " Quantity: " + quantity +
                    " Tax Rate: " + taxRate + " for quote: '" + quote.getQuoteName() + "'");

            return new AddItemCommand(itemName, quote, price, quantity, taxRate);
        } else {
            logger.warning("Invalid format for add item command: " + arguments);
            throw new QuotelyException(
                    QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "add i/ITEM_NAME [n/QUOTE_NAME] p/PRICE q/QUANTITY [t/TAX_RATE]");
        }
    }

    private void validateItemName(String itemName) throws QuotelyException {
        if (itemName.length() > MAX_ITEMNAME_LENGTH || !isValidName(itemName)) {
            logger.warning("Invalid item name for add item command: " + itemName);
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_ITEM_NAME);
        }
    }

    private void checkCapacity(Quote quote) throws QuotelyException {
        if (quote.getItems().size() >= MAX_ITEMS) {
            logger.warning("Invalid item count for quote for add item command");
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_ITEM_NUMBER);
        }
    }

    private Double parsePrice(String priceStr) throws QuotelyException {
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (Double.isNaN(price) || price < 0) {
                throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
            }
            if (price > MAX_PRICE) {
                throw new QuotelyException(QuotelyException.ErrorType.INVALID_ITEM_PRICE);
            }
        } catch (NumberFormatException e) {
            logger.warning("Failed to parse price: " + e.getMessage());
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
        }
        return price;
    }

    private int parseQuantity(String quantityStr) throws QuotelyException {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
            }
            if (quantity > MAX_QTY) {
                throw new QuotelyException(QuotelyException.ErrorType.INVALID_ITEM_QTY);
            }
        } catch (NumberFormatException e) {
            logger.warning("Failed to parse quantity: " + e.getMessage());
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
        }
        return quantity;
    }

    private Double parseTaxRate(String taxRateStr) throws QuotelyException {
        double taxRate = 0;
        if (taxRateStr != null) {
            try {
                taxRate = Double.parseDouble(taxRateStr);
                if (Double.isNaN(taxRate) || taxRate < 0) {
                    throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
                }
                if (taxRate > MAX_TAX_RATE) {
                    throw new QuotelyException(QuotelyException.ErrorType.INVALID_ITEM_TAX);
                }
            } catch (NumberFormatException e) {
                logger.warning("Failed to parse tax rate: " + e.getMessage());
                throw new QuotelyException(QuotelyException.ErrorType.INVALID_NUMBER_FORMAT);
            }
        }
        return taxRate;
    }
}
