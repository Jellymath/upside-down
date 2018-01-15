import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please specify properties file")
        return
    }
    val properties = Properties().apply {
        FileInputStream(args[0]).use { load(it) }
    }
    ApiContextInitializer.init()
    val bot = UpsideDownBot(properties.getProperty("token"), properties.getProperty("name"))
    TelegramBotsApi().registerBot(bot)
}

class UpsideDownBot(private val token: String, private val username: String) : TelegramLongPollingBot() {
    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update?) {
        update?.inlineQuery?.let {
            val result = it.query
                    .toLowerCase()
                    .reversed()
                    .map(::turnUpsideDown).joinToString("")
                    .let { if (it.isBlank()) spookyText else it }
            execute(AnswerInlineQuery().apply {
                inlineQueryId = it.id
                setResults(InlineQueryResultArticle().apply {
                    id = "1"
                    title = result
                    inputMessageContent = InputTextMessageContent().apply { messageText = result }
                })
            })
        }
    }

    private val spookyText = "S̠͉͉͍̻͇̜̬̜͢͡͞ͅǫ͇̳̬͕̩̣̣̭͎̹͔̲̱̱͔̦̜͇̗́̕m̸̪̪͖̠͈͎̮̻̻͔͍̻͕̰͔̪͖̕è̢̲͈͇̖̪̠͎͓̱͖͍̙̲͟ ̪̺̩̬͚̖̹̻̭̱͈͟͝s͏͍͚̣̺̯̪̩͡ͅṕ̢̡̫̘̜̯̩̘͖̥͓̙̬̠͙̤͚̰̤͞ǫ̷̙̻͉̰͎̗͕̙͓͚̻̼̬̣̘̗̥̬͜͜ơ̤͕̩̜̫͍͚͕̠̺͕̮͈͘͡ͅk̸̡͕̩̝͉̟̺̘̤͇̜̻͘͢͝y̛̛̭̪̰͉͚̼̘̖͍͇͠ ̸̵̢̧̢̝̲̖̞t̢͎͍̻̘͇̦̰́͜͟͠e̢̘̤̫̘̯̻̹̣̮͕̕͢x̧͘̕͡҉̺͔͚̘̜̘̖t҉̷͔̖͙̯͈̼̩̤͕̭̼̮̜̰̰͚̙̀ͅͅ ͠҉̧̗͔͎̘̹̦̼̩͓̕ͅf̶̀͜͏̤̙̙̭̻̙̼͍̫̫ó̵̵̜͖̥̱̻͚̘̩̦͎ͅͅr̙̪̠͇̜̥͍̦̘̥̭̀ ̶̢̱̲̲̝̳̪͖̥̩̻̬̟́͞͞T҉̵̷̺̖̞͇̰̞̩͎̤̯͢͞e̡̨̛͍̻̱͉͕̖̩̞̦͜͞l͘҉̢͚̯͇͎̻̲̯̺̝̙̥͠ͅe҉̡̳̬̪͎̞͔͝ͅg̛̼̪̤͓̥̘̠̪̗̣̮̪̖̺̲̳̳̞͜͝ͅr̡͏̴̸͙̩͓̣̖͎̹̼̭̗͇̜͚̝̀ͅͅͅͅa̛̗̹̻̙̪͘ͅḿ̦͉̙̠̭̜̠͉̞̯͍͎̖̠̱͔̘̕͠͞"

    private fun turnUpsideDown(char: Char) = when (char) {
        'a' -> 'ɐ'
        'b' -> 'q'
        'c' -> 'ɔ'
        'd' -> 'p'
        'e' -> 'ǝ'
        'f' -> 'ɟ'
        'g' -> 'ƃ'
        'h' -> 'ɥ'
        'i' -> 'ᴉ'
        'j' -> 'ɾ'
        'k' -> 'ʞ'
        'l' -> 'l'
        'm' -> 'ɯ'
        'n' -> 'u'
        'o' -> 'o'
        'p' -> 'd'
        'q' -> 'b'
        'r' -> 'ɹ'
        's' -> 's'
        't' -> 'ʇ'
        'u' -> 'n'
        'v' -> 'ʌ'
        'w' -> 'ʍ'
        'x' -> 'x'
        'y' -> 'ʎ'
        'z' -> 'z'
        '?' -> '¿'
        '!' -> '¡'
        ',' -> '\''
        '\'' -> ','
        '.' -> '˙'
        '[' -> ']'
        ']' -> '['
        '(' -> ')'
        ')' -> '('
        '{' -> '}'
        '}' -> '{'
        else -> char
    }
}
