import java.io.IOException
import java.io.PrintStream

class PrinterForSystemOutput(override val outputStream: PrintStream) : Printer(outputStream) {
    override fun cleanScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                ProcessBuilder("cmd", "/c", "cls").inheritIO()
                    .start().waitFor()
            }
            else {
                Runtime.getRuntime().exec("clear")
            }
        } catch (ex: IOException) {
            outputStream.println("Not possible to clear the screen!")
        } catch (ex: InterruptedException) {
            outputStream.println("Not possible to clear the screen!")
        }
    }
}