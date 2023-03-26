package seedu.ultron.logic.parser;

import static seedu.ultron.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ultron.logic.commands.StatusCommand;
import seedu.ultron.logic.parser.exceptions.ParseException;
import seedu.ultron.model.opening.StatusContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new StatusCommand object
 */
public class StatusCommandParser implements Parser<StatusCommand> {

    /**
     * Parses the given {@code String} argument in the context of the StatusCommand
     * and returns a StatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatusCommand parse(String arg) throws ParseException {
        String trimmedArg = arg.trim();
        String[] nameKeywords = trimmedArg.split("\\s+");
        if (trimmedArg.isEmpty() || nameKeywords.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
        }
        return new StatusCommand(new StatusContainsKeywordPredicate(trimmedArg));
    }
}
