import java.io.ByteArrayOutputStream

class PrinterFake (override val outputStream: ByteArrayOutputStream) : Printer(outputStream) {
    override fun cleanScreen() {
        outputStream.reset()
    }
}