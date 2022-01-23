# League table reducer

This is a demo application to demonstrate reading text input and producing text output. The application will take text input that describes football matches played and then produces a league table as output.

## Building

Run `./gradlew distZip` to create a distribution for the application.

## Usage

Run `.bin/app --matches matches.txt` to read matches played from a text file and print out the resulting league table to stdout.

Use the `--output <output-file>` option to write the output to a file. For example: `./bin/app --matches matches.txt --output league-table.txt`.

The matches input can also be piped to the app for example: `cat matches.txt | ./bin/app`.
