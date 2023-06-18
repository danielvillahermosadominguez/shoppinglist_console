import java.io.ByteArrayOutputStream

class PrinterForByteArrayOutput (override val outputStream: ByteArrayOutputStream) : Printer(outputStream) {
    override fun cleanScreen() {
        outputStream.reset()
    }
}