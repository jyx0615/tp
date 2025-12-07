package seedu.quotely.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.quotely.command.RegisterCommand;
import seedu.quotely.data.QuoteList;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.exception.QuotelyException;
import static seedu.quotely.parser.ParserUtil.isValidName;
import static seedu.quotely.parser.ParserConstant.MAX_COMPANYNAME_LENGTH;
import static seedu.quotely.parser.ParserConstant.REGISTER_COMMAND_PATTERN;

public class RegisterCommandParser implements Parser {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(RegisterCommandParser.class.getName());
    
    @Override
    public RegisterCommand parse(String arguments, QuotelyState state, 
        QuoteList quoteList) throws QuotelyException {
        logger.fine("parseRegisterCommand called with arguments: " + arguments);

        Pattern p = Pattern.compile(REGISTER_COMMAND_PATTERN);
        Matcher m = p.matcher(arguments);

        if (m.find()) {
            String name = m.group(1).trim();
            validateName(name);

            logger.info("Successfully parsed register command for company: " + name);
            return new RegisterCommand(name);
        } else {
            logger.warning("Invalid format for register command: " + arguments);
            throw new QuotelyException(
                    QuotelyException.ErrorType.WRONG_COMMAND_FORMAT,
                    "register c/COMPANY_NAME");
        }
    }

    private void validateName(String name) throws QuotelyException {
        if (name.length() > MAX_COMPANYNAME_LENGTH || !isValidName(name)) {
            logger.warning("Invalid company name for register command: " + name);
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_COMPANY_NAME);
        }
    }
}
