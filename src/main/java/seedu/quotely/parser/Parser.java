package seedu.quotely.parser;

import seedu.quotely.command.Command;
import seedu.quotely.data.QuotelyState;
import seedu.quotely.data.QuoteList;
import seedu.quotely.exception.QuotelyException;

public interface Parser {
    Command parse(String arguments, QuotelyState state, QuoteList quoteList) throws QuotelyException;
}
