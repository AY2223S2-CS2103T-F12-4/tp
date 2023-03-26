package seedu.ultron.logic.parser;

import static seedu.ultron.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_KEYDATE;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.ultron.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.ultron.logic.commands.AddCommand;
import seedu.ultron.logic.parser.exceptions.ParseException;
import seedu.ultron.model.opening.Company;
import seedu.ultron.model.opening.Date;
import seedu.ultron.model.opening.Email;
import seedu.ultron.model.opening.Opening;
import seedu.ultron.model.opening.Position;
import seedu.ultron.model.opening.Remark;
import seedu.ultron.model.opening.Status;



/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSITION, PREFIX_COMPANY, PREFIX_EMAIL,
                        PREFIX_STATUS, PREFIX_REMARK, PREFIX_KEYDATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_POSITION, PREFIX_COMPANY, PREFIX_EMAIL, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Position position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        Optional<Remark> remark = argMultimap.getValue(PREFIX_REMARK).map(ParserUtil::parseRemark);
        Set<Date> dateList = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_KEYDATE));

        Opening opening = new Opening(position, company, email, status, remark.orElse(null), dateList);

        return new AddCommand(opening);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
