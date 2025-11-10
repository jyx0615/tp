package seedu.quotely.parser;

import java.util.logging.Logger;

import seedu.quotely.exception.QuotelyException;
import seedu.quotely.command.AddItemCommand;
import seedu.quotely.command.AddQuoteCommand;
import seedu.quotely.command.CalculateTotalCommand;
import seedu.quotely.command.Command;
import seedu.quotely.command.DeleteQuoteCommand;
import seedu.quotely.command.DeleteItemCommand;
import seedu.quotely.command.ExitCommand;
import seedu.quotely.command.ExportQuoteCommand;
import seedu.quotely.command.FinishQuoteCommand;
import seedu.quotely.command.NavigateCommand;
import seedu.quotely.command.RegisterCommand;
import seedu.quotely.command.SearchQuoteCommand;
import seedu.quotely.command.ShowQuotesCommand;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.data.QuoteList;


import seedu.quotely.util.LoggerConfig;
public class QuotelyParser {
    private static final Logger logger = LoggerConfig.getLogger(QuotelyParser.class);
    private static QuotelyParser instance = null;

    private QuotelyParser() {
        // Private constructor to prevent instantiation
    }

    public static QuotelyParser getInstance() {
        if (instance == null) {
            instance = new QuotelyParser();
        }
        return instance;
    }

    public Command parse(String fullCommand, QuotelyState state, QuoteList quoteList)
            throws QuotelyException {

        // Precondition assertions
        assert state != null : "QuotelyState cannot be null";
        assert quoteList != null : "QuoteList cannot be null";
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new QuotelyException(QuotelyException.ErrorType.EMPTY_COMMAND);
        }

        logger.info("Parsing command: " + fullCommand);
        logger.fine("Current state - isInside quote: " + state.isInsideQuote() +
                " QuoteReference: "
                + (state.getQuoteReference() != null ? state.getQuoteReference().toString() : "null"));

        /*
         * edit parse method to allow command input depending on isInsideState
         * add exception handling in parser
         */
        fullCommand = fullCommand.trim();
        String command = fullCommand.split(" ")[0].toLowerCase();
        logger.fine("Extracted command: '" + command + "'");

        String arguments = "";
        if (fullCommand.split(" ").length > 1) {
            arguments = fullCommand.split(" ", 2)[1].trim();
            logger.fine("Extracted arguments: '" + arguments + "'");
        }
        switch (command) {
        case RegisterCommand.COMMAND_WORD:
            // available in all state
            return new RegisterCommandParser().parse(arguments, state, quoteList);

        case AddQuoteCommand.COMMAND_WORD:
            // available in all state
            return new AddQuoteCommandParser().parse(arguments, state, quoteList);

        case DeleteQuoteCommand.COMMAND_WORD:
            // can use no quote name if inside a quote
            return new DeleteQuoteCommandParser().parse(arguments, state, quoteList);

        case ShowQuotesCommand.COMMAND_WORD:
            // available in all state, for now?
            return new ShowQuotesCommand();

        case FinishQuoteCommand.COMMAND_WORD:
            // inside quote only
            return new FinishQuoteCommandParser().parse(arguments, state, quoteList);

        case DeleteItemCommand.COMMAND_WORD:
            // can use without quote name if inside a quote
            return new DeleteItemCommandParser().parse(arguments, state, quoteList);

        case ExportQuoteCommand.COMMAND_WORD:
            // can use without quote name if inside a quote
            return new ExportQuoteCommandParser().parse(arguments, state, quoteList);

        case AddItemCommand.COMMAND_WORD:
            // can use without quote name if inside a quote
            return new AddItemCommandParser().parse(arguments, state, quoteList);

        case CalculateTotalCommand.COMMAND_WORD:
            // can use without quote name if inside a quote
            return new TotalCommandParser().parse(arguments, state, quoteList);

        case NavigateCommand.COMMAND_WORD:
            // available in all states, but need to specify target location e.g. 'main' or quoteName
            return new NavigateCommandParser().parse(arguments, state, quoteList);

        case SearchQuoteCommand.COMMAND_WORD:
            // available in main menu only
            return new SearchCommandParser().parse(arguments, state, quoteList);

        case ExitCommand.COMMAND_WORD:
            // available in all state, for now
            return new ExitCommand();
            
        default:
            throw new QuotelyException(QuotelyException.ErrorType.INVALID_COMMAND);
        }
    }
}
